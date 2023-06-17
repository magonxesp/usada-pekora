package com.usadapekora.shared.domain.bus

import kotlinx.datetime.Instant

data class EventProcessed(
    val id: EventId,
    val name: EventName,
    val consumedBy: EventConsumedBy,
    val consumedOn: EventConsumedOn,
    val timeElapsedMilliseconds: EventTimeElapsedMilliseconds
) {
    companion object {
        fun fromPrimitives(
            id: String,
            name: String,
            consumedBy: String,
            consumedOn: Instant,
            timeElapsedMilliseconds: Long
        ) = EventProcessed(
            id = EventId(id),
            name = EventName(name),
            consumedBy = EventConsumedBy(consumedBy),
            consumedOn = EventConsumedOn(consumedOn),
            timeElapsedMilliseconds = EventTimeElapsedMilliseconds(timeElapsedMilliseconds)
        )
    }
}
