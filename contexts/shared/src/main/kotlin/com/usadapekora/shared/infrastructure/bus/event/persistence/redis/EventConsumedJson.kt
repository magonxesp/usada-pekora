package com.usadapekora.shared.infrastructure.bus.event.persistence.redis

import com.usadapekora.shared.domain.bus.event.EventConsumed
import kotlinx.serialization.Serializable

@Serializable
class EventConsumedJson(
    val id: String,
    val consumedBy: String,
) {
    companion object {
        fun fromEntity(entity: EventConsumed) = EventConsumedJson(
            id = entity.id.value,
            consumedBy = entity.consumedBy.value
        )
    }

    fun toEntity() = EventConsumed.fromPrimitives(id = id, consumedBy = consumedBy)
}
