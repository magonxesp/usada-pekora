package com.usadapekora.bot.backend

import com.usadapekora.bot.eventSubscribers
import com.usadapekora.shared.domain.LoggerFactory
import com.usadapekora.shared.domain.bus.event.EventConsumer
import org.koin.java.KoinJavaComponent.inject

private val eventConsumer: EventConsumer by inject(EventConsumer::class.java)
private val loggerFactory: LoggerFactory by inject(LoggerFactory::class.java)
private val logger = loggerFactory.getLogger("com.usadapekora.bot.backend.ConsumertKt")

fun startConsumers() {
    logger.info("Starting consumers...")
    eventConsumer.consume(eventSubscribers).onLeft { throw Exception(it.message) }
}
