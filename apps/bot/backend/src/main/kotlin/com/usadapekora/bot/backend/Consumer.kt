package com.usadapekora.bot.backend

import com.usadapekora.bot.eventSubscribers
import com.usadapekora.bot.events
import com.usadapekora.shared.domain.LoggerFactory
import com.usadapekora.shared.domain.bus.event.DomainEventConsumer
import com.usadapekora.shared.infrastructure.bus.event.DomainEventRegistry
import org.koin.java.KoinJavaComponent.inject

private val registry: DomainEventRegistry by inject(DomainEventRegistry::class.java)
private val eventConsumer: DomainEventConsumer by inject(DomainEventConsumer::class.java)
private val loggerFactory: LoggerFactory by inject(LoggerFactory::class.java)
private val logger = loggerFactory.getLogger("com.usadapekora.bot.backend.ConsumertKt")

fun startConsumers() {
    logger.info("Starting consumers...")
    registry.registerDomainEvents(events)
    registry.registerDomainEventSubscribers(eventSubscribers)
    eventConsumer.consume().onLeft { throw Exception(it.message) }
}
