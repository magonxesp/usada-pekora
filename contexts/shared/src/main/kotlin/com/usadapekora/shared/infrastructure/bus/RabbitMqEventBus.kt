package com.usadapekora.shared.infrastructure.bus

import arrow.core.Either
import com.rabbitmq.client.ConnectionFactory
import com.usadapekora.shared.domain.bus.Event
import com.usadapekora.shared.domain.bus.EventBus
import com.usadapekora.shared.domain.bus.EventBusError
import com.usadapekora.shared.rabbitMqUrl
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.modules.SerializersModule

class RabbitMqEventBus(private val serializerModule: SerializersModule) : EventBus {

    val jsonEncoder = Json {
        serializersModule = serializerModule
        charset(Charsets.UTF_8.name())
    }

    private val exchangeName = "usadapekora"
    private val queueNamePrefix = "usadapekora.event"
    private val routingKeyPrefix = "event"

    override fun dispatch(vararg events: Event): Either<EventBusError, Unit> = Either.catch {
        for (event in events) {
            val message = jsonEncoder.encodeToString(event)
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
