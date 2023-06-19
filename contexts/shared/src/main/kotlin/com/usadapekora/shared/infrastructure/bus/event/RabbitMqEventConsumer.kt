package com.usadapekora.shared.infrastructure.bus.event

import arrow.core.Either
import com.rabbitmq.client.*
import com.usadapekora.shared.EventSubscribers
import com.usadapekora.shared.domain.bus.event.*
import com.usadapekora.shared.domain.getAnnotation
import com.usadapekora.shared.infrastructure.serialization.createJacksonObjectMapperInstance
import com.usadapekora.shared.rabbitMqUrl
import kotlinx.datetime.Clock
import org.koin.java.KoinJavaComponent.inject
import java.util.logging.Logger
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.system.measureTimeMillis

class RabbitMqEventConsumer(
    private val eventConsumedRepository: EventConsumedRepository,
    private val eventProcessedRepository: EventProcessedRepository,
    private val clock: Clock
) : EventConsumer {
    private val logger = Logger.getLogger(this::class.toString())

    private inner class SubscriberConsumer(
        channel: Channel,
        private val subscriberClass: KClass<*>,
        private val eventClass: KClass<*>
    ) : DefaultConsumer(channel) {
        private val mapper = createJacksonObjectMapperInstance()

        override fun handleDelivery(
            consumerTag: String?,
            envelope: Envelope?,
            properties: AMQP.BasicProperties?,
            body: ByteArray?
        ) {
            try {
                if (!eventClass.isSubclassOf(Event::class)) {
                    return
                }

                val event = mapper.readValue(body, eventClass.java) as Event
                val subscriber: EventSubscriber<Event> by inject(subscriberClass.java)
                var result: Either<EventSubscriberError, Unit>

                eventConsumedRepository.find(
                    id = EventConsumed.EventConsumedId(event.id),
                    consumedBy = EventConsumed.EventConsumedBy(subscriberClass.toString())
                )
                .onLeft { eventConsumedRepository.save(EventConsumed.fromPrimitives(event.id, subscriberClass.toString())) }
                .onRight { return }

                // second verification if the event is really processed
                eventProcessedRepository.find(
                    id = EventProcessed.EventProcessedId(event.id),
                    consumedBy = EventProcessed.EventProcessedConsumedBy(subscriberClass.toString())
                ).onRight { return }

                val timeElapsed = measureTimeMillis {
                    result = subscriber.handle(event)
                }

                result
                    .onLeft {
                        logger.warning("Failed to consume event ${event.name} with id ${event.id}: ${it.message}")
                        channel.basicReject(envelope!!.deliveryTag, false)
                    }
                    .onRight {
                        eventProcessedRepository.save(
                            EventProcessed.fromPrimitives(
                                id = event.id,
                                name = event.name,
                                consumedBy = subscriberClass.toString(),
                                consumedOn = clock.now(),
                                timeElapsedMilliseconds = timeElapsed
                            )
                        )
                        channel.basicAck(envelope!!.deliveryTag, false)
                        logger.info("Event ${event.name} with id ${event.id} successfully consumed")
                    }
            } catch (e: Exception) {
                logger.warning("Failed to consume event: ${e.message}")
                channel.basicReject(envelope!!.deliveryTag, false)
            }
        }
    }

    override fun startConsume(subscribers: EventSubscribers): Either<EventConsumerError, Unit> = Either.catch {
        val connection = ConnectionFactory()
            .apply { setUri(rabbitMqUrl) }
            .newConnection()

        subscribers
            .filter { it.isSubclassOf(EventSubscriber::class) }
            .forEach { subscriberClass ->
                val subscribesTo = getAnnotation<SubscribesEvent<*>>(subscriberClass)
                val eventName = getAnnotation<EventName>(subscribesTo.eventClass).name
                val queue = RabbitMqNameFormatter.queueName(RabbitMqNameFormatter.QueueType.EVENT, eventName)

                connection.use {
                    it.createChannel().use { channel ->
                        channel.basicConsume(queue, SubscriberConsumer(channel, subscriberClass, subscribesTo.eventClass))
                    }
                }
            }
    }.mapLeft { EventConsumerError(it.message) }
}
