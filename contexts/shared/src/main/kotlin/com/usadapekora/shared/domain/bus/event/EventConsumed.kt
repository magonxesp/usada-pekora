package com.usadapekora.shared.domain.bus.event

import com.usadapekora.shared.domain.Entity
import com.usadapekora.shared.domain.valueobject.UuidValueObject

data class EventConsumed(
    val id: EventConsumedId,
    val consumedBy: EventConsumedBy,
) : Entity() {
    data class EventConsumedId(override val value: String) : UuidValueObject(value = value) {
        init {
            validate()
        }
    }
    data class EventConsumedBy(val value: String)

    companion object {
        fun fromPrimitives(
            id: String,
            consumedBy: String,
        ) = EventConsumed(
            id = EventConsumedId(id),
            consumedBy = EventConsumedBy(consumedBy),
        )
    }

    override fun id() = id.value
}
