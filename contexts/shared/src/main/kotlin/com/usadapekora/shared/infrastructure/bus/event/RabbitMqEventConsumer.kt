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
import kotlin.reflect.KClass
import kotlin.system.measureTimeMillis

class RabbitMqEventConsumer(
    private val eventConsumedRepository: EventConsumedRepository,
    private val eventProcessedRepository: EventProcessedRepository,
    private val clock: Clock
) : EventConsumer {
    private inner class SubscriberConsumer(
        channel: Channel,
        private val subscriberClass: KClass<EventSubscriber<Event>>,
        private val eventClass: KClass<Event>
    ) : DefaultConsumer(channel) {
        private val mapper = createJacksonObjectMapperInstance()

        override fun handleDelivery(
            consumerTag: String?,
            envelope: Envelope?,
            properties: AMQP.BasicProperties?,
            body: ByteArray?
        ) {
            try {
                val event = mapper.readValue(body, eventClass.java)
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
                    .onLeft { channel.basicReject(envelope!!.deliveryTag, false) }
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
                    }

            } catch (e: Exception) {
                channel.basicReject(envelope!!.deliveryTag, false)
            }
        }
    }

    override fun startConsume(subscribers: EventSubscribers) {
        val connection = ConnectionFactory()
            .apply { setUri(rabbitMqUrl) }
            .newConnection()

        subscribers.forEach { subscriberClass ->
            val subscribesTo = getAnnotation<SubscribesEvent>(subscriberClass)
            val eventName = getAnnotation<EventName>(subscribesTo.event).name
            val queue = RabbitMqNameFormatter.queueName(RabbitMqNameFormatter.QueueType.EVENT, eventName)

            connection.use {
                it.createChannel().use { channel ->
                    channel.basicConsume(queue, SubscriberConsumer(channel, subscriberClass, subscribesTo.event))
                }
            }
        }
    }
}
