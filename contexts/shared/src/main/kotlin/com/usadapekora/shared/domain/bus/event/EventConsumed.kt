package com.usadapekora.shared.domain.bus.event

data class EventConsumed(
    val id: EventId,
    val consumedBy: EventConsumedBy,
) {
    companion object {
        fun fromPrimitives(
            id: String,
            consumedBy: String,
        ) = EventConsumed(
            id = EventId(id),
            consumedBy = EventConsumedBy(consumedBy),
        )
    }
}
