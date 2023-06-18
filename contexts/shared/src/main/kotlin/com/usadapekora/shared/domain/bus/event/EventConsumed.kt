package com.usadapekora.shared.domain.bus.event

import com.usadapekora.shared.domain.valueobject.UuidValueObject

data class EventConsumed(
    val id: EventConsumedId,
    val consumedBy: EventConsumedConsumedBy,
) {
    data class EventConsumedId(override val value: String) : UuidValueObject(value = value)
    data class EventConsumedConsumedBy(val value: String)

    companion object {
        fun fromPrimitives(
            id: String,
            consumedBy: String,
        ) = EventConsumed(
            id = EventConsumedId(id),
            consumedBy = EventConsumedConsumedBy(consumedBy),
        )
    }
}
