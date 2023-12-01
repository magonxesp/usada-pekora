package com.usadapekora.shared.infrastructure.bus

import com.rabbitmq.client.Channel
import com.usadapekora.shared.domain.bus.command.Command
import com.usadapekora.shared.domain.bus.event.DomainEvent
import com.usadapekora.shared.domain.bus.event.DomainEventSubscriber
import com.usadapekora.shared.domain.camelToSnakeCase
import com.usadapekora.shared.domain.isSubsclassOfAbstract
import com.usadapekora.shared.infrastructure.bus.command.CommandRegistry
import com.usadapekora.shared.infrastructure.bus.event.DomainEventRegistry
import com.usadapekora.shared.serviceContainer
import kotlin.reflect.KClass

private val queuePrefix = "usada_pekora"

// Domain events
val domainEventExchangeName = "$queuePrefix.domain_events"
val domainEventDeadLetterExchangeName = "$queuePrefix.domain_events.dead_letter"

// Commands
val commandExchangeName = "$queuePrefix.commands"
val commandDeadLetterExchangeName = "$queuePrefix.commands.dead_letter"

fun domainEventSubscriberQueueName(klass: KClass<*>): String {
    if (!klass.isSubsclassOfAbstract(DomainEventSubscriber::class)) {
        throw RuntimeException("The domain event subscriber $klass should extends ${DomainEventSubscriber::class}")
    }

    return "$queuePrefix.domain_event_subscriber.${klass.simpleName!!.camelToSnakeCase()}"
}

fun domainEventDeadLetterSubscriberQueueName(klass: KClass<*>): String =
    domainEventSubscriberQueueName(klass) + ".dead_letter"

fun domainEventRoutingKey(domainEventName: String) =
    "$queuePrefix.domain_event.$domainEventName"

fun commandQueueName(klass: KClass<*>): String {
    if (!klass.isSubsclassOfAbstract(Command::class)) {
        throw RuntimeException("The command $klass should extends ${Command::class}")
    }

    return "$queuePrefix.command.${klass.simpleName!!.camelToSnakeCase()}"
}

fun commandDeadLetterSubscriberQueueName(klass: KClass<*>): String =
    commandQueueName(klass) + ".dead_letter"

fun commandRoutingKey(kClass: KClass<*>) = commandQueueName(kClass)

fun Channel.createDomainEventSubscriberQueue(domainEventName: String, subscriberClass: KClass<*>) {
    val routingKey = domainEventRoutingKey(domainEventName)
    val deadLetterQueueName = domainEventDeadLetterSubscriberQueueName(subscriberClass)
    val queueName = domainEventSubscriberQueueName(subscriberClass)
    val args = mapOf(
        "x-dead-letter-exchange" to domainEventDeadLetterExchangeName,
        "x-dead-letter-routing-key" to deadLetterQueueName
    )

    // declare the dead letter queue
    exchangeDeclare(domainEventDeadLetterExchangeName, "direct", true)
    queueDeclare(deadLetterQueueName, true, false, false, args)
    queueBind(deadLetterQueueName, domainEventDeadLetterExchangeName, deadLetterQueueName)

    // declare the normal queue
    exchangeDeclare(domainEventExchangeName, "direct", true)
    queueDeclare(queueName, true, false, false, args)
    queueBind(queueName, domainEventExchangeName, routingKey)
}

fun Channel.createCommandQueue(klass: KClass<*>) {
    val queueName = commandQueueName(klass)
    val deadLetterQueueName = commandDeadLetterSubscriberQueueName(klass)

    val args = mapOf(
        "x-dead-letter-exchange" to commandDeadLetterExchangeName,
        "x-dead-letter-routing-key" to deadLetterQueueName
    )

    // declare the dead letter queue
    exchangeDeclare(commandDeadLetterExchangeName, "direct", true)
    queueDeclare(deadLetterQueueName, true, false, false, args)
    queueBind(deadLetterQueueName, commandDeadLetterExchangeName, deadLetterQueueName)

    // declare the normal queue
    exchangeDeclare(commandExchangeName, "direct", true)
    queueDeclare(queueName, true, false, false, args)
    queueBind(queueName, commandExchangeName, queueName)
}

fun Channel.createDomainEventQueues() {
    val registry = serviceContainer().get<DomainEventRegistry>(DomainEventRegistry::class)

    registry.subscribers.forEach { entry ->
        entry.value.forEach { createDomainEventSubscriberQueue(entry.key, it) }
    }
}

fun Channel.createCommandQueues() {
    val registry = serviceContainer().get<CommandRegistry>(CommandRegistry::class)
    registry.handlers.forEach { entry -> createCommandQueue(entry.key) }
}
