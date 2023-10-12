package com.usadapekora.shared.domain.bus.event

import kotlinx.datetime.Instant
import java.io.Serializable

interface DomainEventFactory<T : DomainEvent> {
    fun fromPrimitives(id: String, occurredOn: Instant, name: String, attributes: Map<String, Serializable>): T
}
