package com.usadapekora.shared.infrastructure.bus.event

import arrow.core.Either
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import com.usadapekora.shared.domain.bus.event.Event
import com.usadapekora.shared.domain.bus.event.EventBus
import com.usadapekora.shared.domain.bus.event.EventBusError
import com.usadapekora.shared.infrastructure.serialization.createJacksonObjectMapperInstance
import com.usadapekora.shared.rabbitMqUser

class RabbitMqEventBus : EventBus {

    private val mapper = createJacksonObjectMapperInstance()
    private val exchangeName = RabbitMqNameFormatter.exchange

    private fun createDeadLetterQueue(event: Event, channel: Channel) {
        val queueName = RabbitMqNameFormatter.deadLetterQueueName(RabbitMqNameFormatter.QueueType.EVENT, event.name)
        val routingKey = RabbitMqNameFormatter.deadLetterRoutingKey(RabbitMqNameFormatter.QueueType.EVENT, event.name)

        channel.exchangeDeclare(exchangeName, "direct", true)
        channel.queueDeclare(queueName, true, false, false, null)
        channel.queueBind(queueName, exchangeName, routingKey)
    }

    /**
     * Returns the routing key when created
     */
    private fun createQueue(event: Event, channel: Channel): String {
        val queueName = RabbitMqNameFormatter.queueName(RabbitMqNameFormatter.QueueType.EVENT, event.name)
        val routingKey = RabbitMqNameFormatter.routingKey(RabbitMqNameFormatter.QueueType.EVENT, event.name)
        val deadLetterRoutingKey = RabbitMqNameFormatter.deadLetterRoutingKey(RabbitMqNameFormatter.QueueType.EVENT, event.name)
//        val args = mapOf(
//            "x-dead-letter-exchange" to exchangeName,
//            "x-dead-letter-routing-key" to deadLetterRoutingKey
//        )

        channel.exchangeDeclare(exchangeName, "direct", true)
        channel.queueDeclare(queueName, true, false, false, null)
        channel.queueBind(queueName, exchangeName, routingKey)

        return routingKey
    }

    override fun dispatch(vararg events: Event): Either<EventBusError, Unit> = Either.catch {
        var exception: Throwable? = null
        val connection = ConnectionFactory()
            .apply { setUri(rabbitMqUser) }
            .newConnection()

        try {
            val channel = connection.createChannel()

            for (event in events) {
                val message = mapper.writeValueAsString(event)
                //createDeadLetterQueue(event, channel)
                val routingKey = createQueue(event, channel)
                channel.basicPublish(exchangeName, routingKey, null, message.toByteArray())
            }
        } catch (e: Throwable) {
            exception = e
        }

        connection.close(500)

        if (exception != null) {
            throw exception
        }
    }.mapLeft { EventBusError(message = it.message) }

}
