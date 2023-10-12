package com.usadapekora.shared.infrastructure.bus.event

import com.usadapekora.shared.DomainEventSubscribers
import com.usadapekora.shared.DomainEvents

class DomainEventRegistry {
    private var registeredEvents: DomainEvents = mapOf()
    private var registeredSubscribers: DomainEventSubscribers = mapOf()

    fun registerDomainEvents(events: DomainEvents) {
        registeredEvents = events
    }

    fun registerDomainEventSubscribers(subscribers: DomainEventSubscribers) {
        registeredSubscribers = subscribers
    }

    val events: DomainEvents
        get() = registeredEvents

    val subscribers: DomainEventSubscribers
        get() = registeredSubscribers
}
