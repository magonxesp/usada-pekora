package com.usadapekora.shared.infrastructure.bus.event

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Consumer
import com.rabbitmq.client.DefaultConsumer
import com.usadapekora.shared.domain.bus.event.*
import com.usadapekora.shared.domain.getAnnotation
import com.usadapekora.shared.rabbitMqUrl
import kotlin.reflect.KClass

class RabbitMqEventConsumer(private val subscribers: Array<KClass<EventSubscriber<Event>>>) : EventConsumer {
    private class Consumer()


    override fun startConsume() {
        val connection = ConnectionFactory()
            .apply { setUri(rabbitMqUrl) }
            .newConnection()

        subscribers.forEach { subscriberClass ->
            val subscribesTo = getAnnotation<SubscribesEvent>(subscriberClass)
            val eventName = getAnnotation<EventName>(subscribesTo.event).name
            val queue = RabbitMqNameFormatter.queueName(RabbitMqNameFormatter.QueueType.EVENT, eventName)

            connection.use {
                it.createChannel().use { channel ->
                    channel.basicConsume(queue, DefaultConsumer(channel))
                }
            }
        }
    }
}
