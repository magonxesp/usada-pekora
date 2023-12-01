package com.usadapekora.shared.infrastructure.bus.event

import com.usadapekora.shared.domain.bus.event.DomainEvent
import java.io.Serializable
import java.util.*

data class DomainEventJson(
    val id: String = UUID.randomUUID().toString(),
    val occurredOn: String,
    val name: String,
    val attributes: Map<String, Serializable>
) {
    companion object {
        fun fromDomainEvent(event: DomainEvent) = DomainEventJson(
            id = event.eventId,
            occurredOn = event.occurredOn.toString(),
            name = event.eventName,
            attributes = event.attributes()
        )
    }
}
