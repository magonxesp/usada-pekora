package com.usadapekora.shared.infrastructure.bus.event

object RabbitMqNameFormatter {
    enum class QueueType {
        EVENT, COMMAND
    }

    const val exchange = "usadapekora"

    fun routingKey(type: QueueType, eventName: String)
        = "${type.name.lowercase()}.$eventName"

    fun deadLetterRoutingKey(type: QueueType, eventName: String)
        = "${routingKey(type, eventName)}.dead_letter"

    fun queueName(type: QueueType, eventName: String)
        = "$exchange.${type.name.lowercase()}.$eventName"

    fun deadLetterQueueName(type: QueueType, eventName: String)
        = "${queueName(type, eventName)}.dead_letter"
}
