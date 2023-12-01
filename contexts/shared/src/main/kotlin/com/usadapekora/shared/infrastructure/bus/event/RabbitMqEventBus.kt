package com.usadapekora.shared.infrastructure.bus.event

import arrow.core.Either
import com.rabbitmq.client.Channel
import com.usadapekora.shared.domain.LoggerFactory
import com.usadapekora.shared.domain.bus.event.DomainEvent
import com.usadapekora.shared.domain.bus.event.DomainEventBus
import com.usadapekora.shared.domain.bus.event.DomainEventBusException
import com.usadapekora.shared.infrastructure.bus.RabbitMqConnectionFactory
import com.usadapekora.shared.infrastructure.bus.createDomainEventQueues
import com.usadapekora.shared.infrastructure.bus.domainEventExchangeName
import com.usadapekora.shared.infrastructure.bus.domainEventRoutingKey

class RabbitMqEventBus(
    private val eventSerializer: DomainEventSerializer,
    loggerFactory: LoggerFactory
) : DomainEventBus {
    private val logger = loggerFactory.getLogger(this::class)

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

    override fun dispatch(vararg events: DomainEvent): Either<DomainEventBusException, Unit> = Either.catch {
        val connection = RabbitMqConnectionFactory.getConnection()
        val channel = connection.createChannel()

        createDomainEventSubscriberQueues(channel)

        for (event in events) {
            val message = eventSerializer.serialize(event)
            channel.basicPublish(domainEventExchangeName, domainEventRoutingKey(event.eventName), null, message.toByteArray())
            logger.info("Domain event ${event::class} published to RabbitMQ with message body: $message")
        }
    }.mapLeft { DomainEventBusException(message = it.message) }

}
