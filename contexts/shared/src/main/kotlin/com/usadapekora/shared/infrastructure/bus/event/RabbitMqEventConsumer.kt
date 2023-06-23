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
    private val mapper = createJacksonObjectMapperInstance()
    private val consumers = mutableListOf<String>()

    companion object {
        private var connection: Connection? = null

        fun getConnection(): Connection {
            connection = connection ?: ConnectionFactory()
                .apply { setUri(rabbitMqUrl) }
                .newConnection()

            return connection!!
        }
    }

    private fun saveConsumedEvent(event: Event, subscriberClass: KClass<*>) {
        eventConsumedRepository.save(
            EventConsumed.fromPrimitives(
                id = event.id,
                consumedBy = subscriberClass.toString()
            )
        )
    }

    private fun saveProcessedEvent(event: Event, subscriberClass: KClass<*>, timeElapsed: Long) {
        eventProcessedRepository.save(
            EventProcessed.fromPrimitives(
                id = event.id,
                name = event.name,
                consumedBy = subscriberClass.toString(),
                consumedOn = clock.now(),
                timeElapsedMilliseconds = timeElapsed
            )
        )
    }

    private fun isConsumedOrProcessed(event: Event, subscriberClass: KClass<*>): Boolean {
        eventConsumedRepository.find(
            id = EventConsumed.EventConsumedId(event.id),
            consumedBy = EventConsumed.EventConsumedBy(subscriberClass.toString())
        ).apply {
            onLeft { saveConsumedEvent(event, subscriberClass) }
            onRight { return true }
        }

        // second verification if the event is really processed
        eventProcessedRepository.find(
            id = EventProcessed.EventProcessedId(event.id),
            consumedBy = EventProcessed.EventProcessedConsumedBy(subscriberClass.toString())
        ).onRight { return true }

        return false
    }

    private fun createSubscriberDeliverCallback(subscriberClass: KClass<*>, eventClass: KClass<*>, channel: Channel)
        = DeliverCallback { consumerTag: String, delivery: Delivery ->
            try {
                if (!eventClass.isSubclassOf(Event::class)) {
                    return@DeliverCallback
                }

                val event = mapper.readValue(delivery.body.decodeToString(), eventClass.java) as Event
                val subscriber: EventSubscriber<Event> by inject(subscriberClass.java)
                var result: Either<EventSubscriberError, Unit>

                if (isConsumedOrProcessed(event, subscriberClass)) {
                    return@DeliverCallback
                }

                val timeElapsed = measureTimeMillis {
                    result = subscriber.handle(event)
                }

                result
                    .onLeft {
                        logger.warning("Failed to consume event ${event.name} with id ${event.id}: ${it.message}")
                        throw Exception(it.message)
                    }
                    .onRight {
                        channel.basicAck(delivery.envelope.deliveryTag, false)
                        saveProcessedEvent(event, subscriberClass, timeElapsed)
                        logger.info("Event ${event.name} with id ${event.id} successfully consumed")
                    }
            } catch (e: Throwable) {
                logger.warning("Failed to consume event: ${e.message}")
                channel.basicReject(delivery.envelope.deliveryTag, false)
            }
        }

    private fun createConsumer(subscriberClass: KClass<*>, channel: Channel) {
        val subscribesTo = getAnnotation<SubscribesEvent<*>>(subscriberClass)
        val eventName = getAnnotation<EventName>(subscribesTo.eventClass).name
        val queue = RabbitMqNameFormatter.queueName(RabbitMqNameFormatter.QueueType.EVENT, eventName)

        channel.queueDeclare(queue, true, false, false, null)
        val consumer = channel.basicConsume(
            queue,
            false,
            subscriberClass.java.toString(),
            createSubscriberDeliverCallback(subscriberClass, subscribesTo.eventClass, channel),
            CancelCallback { logger.warning("Consume cancelled for $it") }
        )

        consumers.add(consumer)
    }

    override fun consume(subscribers: EventSubscribers): Either<EventConsumerError, Unit> = Either.catch {
        val connection = getConnection()
        val channel = connection.createChannel()

        subscribers
            .filter { it.isSubclassOf(EventSubscriber::class) }
            .forEach { createConsumer(it, channel)}
    }.mapLeft { EventConsumerError(it.message) }
}
