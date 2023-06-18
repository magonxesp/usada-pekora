package com.usadapekora.shared.infrastructure.bus.event

object RabbitMqNameFormatter {
    enum class QueueType {
        EVENT, COMMAND
    }

    const val domainName = "usadapekora"

    fun routingKey(type: QueueType, eventName: String)
        = "${type.name.lowercase()}.$eventName"

    fun queueName(type: QueueType, eventName: String)
        = "$domainName.${type.name.lowercase()}.$eventName"
}
