package com.usadapekora.shared.infrastructure.bus.event

import arrow.core.Either
import com.rabbitmq.client.ConnectionFactory
import com.usadapekora.shared.domain.bus.event.Event
import com.usadapekora.shared.domain.bus.event.EventBus
import com.usadapekora.shared.domain.bus.event.EventBusError
import com.usadapekora.shared.infrastructure.serialization.createJacksonObjectMapperInstance
import com.usadapekora.shared.rabbitMqUrl

class RabbitMqEventBus : EventBus {

    private val mapper = createJacksonObjectMapperInstance()
    private val exchangeName = "usadapekora"
    private val queueNamePrefix = "usadapekora.event"
    private val routingKeyPrefix = "event"

    override fun dispatch(vararg events: Event): Either<EventBusError, Unit> = Either.catch {
        for (event in events) {
            val message = mapper.writeValueAsString(event)
            val queueName = "$queueNamePrefix.${event.name}"
            val routingKey = "$routingKeyPrefix.${event.name}"
            val connection = ConnectionFactory()
                .apply { setUri(rabbitMqUrl) }
                .newConnection()

            connection.use {
                it.createChannel().use { channel ->
                    channel.exchangeDeclare(exchangeName, "direct", true)
                    channel.queueDeclare(queueName, true, false, true, null)
                    channel.queueBind(queueName, exchangeName, routingKey)
                    channel.basicPublish(exchangeName, routingKey, null, message.toByteArray())
                }
            }
        }
    }.mapLeft { EventBusError(message = it.message) }

}
