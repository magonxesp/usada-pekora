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

    override fun dispatch(vararg events: Event): Either<EventBusError, Unit> = Either.catch {
        for (event in events) {
            val message = jsonEncoder.encodeToString(event)
            val connection = ConnectionFactory()
                .apply { setUri(rabbitMqUrl) }
                .newConnection()

            connection.use {
                it.createChannel().use { channel ->
                    channel.exchangeDeclare("usadapekora", "direct", true)
                    channel.queueDeclare("usadapekora.event", true, false, true, null)
                    channel.basicPublish("usadapekora", "event", null, message.toByteArray())
                }
            }
        }
    }.mapLeft { EventBusError(message = it.message) }

}
