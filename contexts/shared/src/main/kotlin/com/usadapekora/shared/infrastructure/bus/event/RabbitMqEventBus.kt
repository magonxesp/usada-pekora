package com.usadapekora.shared.infrastructure.bus.event

import arrow.core.Either
import com.rabbitmq.client.Channel
import com.usadapekora.shared.domain.bus.event.DomainEvent
import com.usadapekora.shared.domain.bus.event.DomainEventBus
import com.usadapekora.shared.domain.bus.event.DomainEventBusException
import com.usadapekora.shared.infrastructure.serialization.createJacksonObjectMapperInstance

class RabbitMqEventBus(
    private val eventSerializer: DomainEventSerializer
) : DomainEventBus {

    private val mapper = createJacksonObjectMapperInstance()
    private val exchangeName = RabbitMqNameFormatter.exchange

    private fun createDeadLetterQueue(event: DomainEvent, channel: Channel) {
        val queueName = RabbitMqNameFormatter.deadLetterQueueName(RabbitMqNameFormatter.QueueType.EVENT, event.name)
        val routingKey = RabbitMqNameFormatter.deadLetterRoutingKey(RabbitMqNameFormatter.QueueType.EVENT, event.name)

        channel.exchangeDeclare(exchangeName, "direct", true)
        channel.queueDeclare(queueName, true, false, false, null)
        channel.queueBind(queueName, exchangeName, routingKey)
    }

    /**
     * Returns the routing key when created
     */
    private fun createQueue(event: DomainEvent, channel: Channel): String {
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

    override fun dispatch(vararg events: DomainEvent): Either<DomainEventBusException, Unit> = Either.catch {
        var exception: Throwable? = null
        val connection = RabbitMqConnectionFactory.getConnection()

        try {
            val channel = connection.createChannel()

            for (event in events) {
                val message = eventSerializer.serialize(event)
                //createDeadLetterQueue(event, channel)
                val routingKey = createQueue(event, channel)
                channel.basicPublish(exchangeName, routingKey, null, message.toByteArray())
            }
        } catch (e: Throwable) {
            exception = e
        }

        if (exception != null) {
            throw exception
        }
    }.mapLeft { DomainEventBusException(message = it.message) }

}
