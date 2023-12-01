package com.usadapekora.shared.infrastructure.bus.event

import arrow.core.Either
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DeliverCallback
import com.usadapekora.shared.domain.LoggerFactory
import com.usadapekora.shared.domain.bus.event.DomainEventConsumer
import com.usadapekora.shared.domain.bus.event.DomainEventConsumerException
import com.usadapekora.shared.infrastructure.bus.RabbitMqConnectionFactory
import com.usadapekora.shared.infrastructure.bus.createDomainEventQueues
import com.usadapekora.shared.infrastructure.bus.domainEventSubscriberQueueName
import kotlin.reflect.KClass

class RabbitMqEventConsumer(
    private val registry: DomainEventRegistry,
    private val eventDeserializer: DomainEventDeserializer,
    private val eventSubscriberDispatcher: DomainEventSubscriberDispatcher,
    loggerFactory: LoggerFactory
) : DomainEventConsumer {
    private val logger = loggerFactory.getLogger(this::class.toString())
    private val consumers = mutableListOf<String>()

    companion object {
        private var queuesCreated = false
    }

    private fun createDomainEventSubscriberQueues(channel: Channel) {
        if (!queuesCreated) {
            logger.info("Creating the domain event subscribers queues on RabbitMQ")
            channel.createDomainEventQueues()
            queuesCreated = true
            logger.info("Domain event subscribers queues on RabbitMQ created")
        }
    }

    private fun onMessageCallback(channel: Channel, subscribersClass: KClass<*>) = DeliverCallback { queue, message ->
        val event = eventDeserializer.deserialize(message.body.decodeToString())

        eventSubscriberDispatcher.executeSubscriber(event, subscribersClass).onRight {
            channel.basicAck(message.envelope.deliveryTag, false)
        }.onLeft {
            channel.basicReject(message.envelope.deliveryTag, false)
        }
    }

    private fun onCancelCallback() = CancelCallback {
        logger.warning("Consume cancelled for $it")
    }

    private fun startConsumeEventSubscribers(channel: Channel, subscribersClass: Array<KClass<*>>) =
        subscribersClass.forEach { subscriberClass ->
            logger.info("Starting listening for incoming RabbitMQ messages of the domain event subscriber $subscriberClass")
            val queueName = domainEventSubscriberQueueName(subscriberClass)

            channel.basicConsume(
                queueName,
                false,
                this::class.toString(),
                onMessageCallback(channel, subscriberClass),
                onCancelCallback()
            ).also {
                consumers.add(it)
            }
        }

    override fun consume(): Either<DomainEventConsumerException, Unit> = Either.catch {
        logger.info("Starting RabbitMQ domain event consumer")
        val connection = RabbitMqConnectionFactory.getConnection()
        val channel = connection.createChannel()

        createDomainEventSubscriberQueues(channel)

        registry.subscribers.values.forEach { subscribersClasses ->
            startConsumeEventSubscribers(channel, subscribersClasses)
        }

        logger.info("The RabbitMQ domain event consumer is running")
    }.mapLeft { DomainEventConsumerException(it.message) }
}
