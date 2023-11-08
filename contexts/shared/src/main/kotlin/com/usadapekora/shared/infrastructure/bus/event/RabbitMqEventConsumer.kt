package com.usadapekora.shared.infrastructure.bus.event

import arrow.core.Either
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.DeliverCallback
import com.usadapekora.shared.domain.LoggerFactory
import com.usadapekora.shared.domain.bus.event.DomainEventConsumer
import com.usadapekora.shared.domain.bus.event.DomainEventConsumerException

class RabbitMqEventConsumer(
    private val registry: DomainEventRegistry,
    private val eventDeserializer: DomainEventDeserializer,
    private val eventSubscriberDispatcher: DomainEventSubscriberDispatcher,
    loggerFactory: LoggerFactory
) : DomainEventConsumer {
    private val logger = loggerFactory.getLogger(this::class.toString())
    private val consumers = mutableListOf<String>()

    override fun consume(): Either<DomainEventConsumerException, Unit> = Either.catch {
        val connection = RabbitMqConnectionFactory.getConnection()
        val channel = connection.createChannel()

        for (eventName in registry.events.keys) {
            val queue = RabbitMqNameFormatter.queueName(RabbitMqNameFormatter.QueueType.EVENT, eventName)
            channel.queueDeclare(queue, true, false, false, null)

            val consumer = channel.basicConsume(
                queue,
                false,
                this::class.toString(),
                DeliverCallback { _, message ->
                    val event = eventDeserializer.deserialize(message.body.decodeToString())
                    eventSubscriberDispatcher.dispatch(event).onRight {
                        channel.basicAck(message.envelope.deliveryTag, false)
                    }.onLeft {
                        channel.basicReject(message.envelope.deliveryTag, false)
                    }
                },
                CancelCallback { logger.warning("Consume cancelled for $it") }
            )

            consumers.add(consumer)
        }
    }.mapLeft { DomainEventConsumerException(it.message) }
}
