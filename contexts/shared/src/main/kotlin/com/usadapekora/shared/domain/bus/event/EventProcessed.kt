package com.usadapekora.shared.domain.bus.event

import com.usadapekora.shared.domain.Entity
import com.usadapekora.shared.domain.valueobject.UuidValueObject
import kotlinx.datetime.Instant

data class EventProcessed(
    val id: EventProcessedId,
    val name: EventProcessedName,
    val consumedBy: EventProcessedConsumedBy,
    val consumedOn: EventProcessedConsumedOn,
    val timeElapsedMilliseconds: EventProcessedEventTimeElapsedMilliseconds
) : Entity() {
    data class EventProcessedId(override val value: String) : UuidValueObject(value = value)
    data class EventProcessedName(val value: String)
    data class EventProcessedConsumedBy(val value: String)
    data class EventProcessedConsumedOn(val value: Instant)
    data class EventProcessedEventTimeElapsedMilliseconds(val value: Long)

    companion object {
        fun fromPrimitives(
            id: String,
            name: String,
            consumedBy: String,
            consumedOn: Instant,
            timeElapsedMilliseconds: Long
        ) = EventProcessed(
            id = EventProcessedId(id),
            name = EventProcessedName(name),
            consumedBy = EventProcessedConsumedBy(consumedBy),
            consumedOn = EventProcessedConsumedOn(consumedOn),
            timeElapsedMilliseconds = EventProcessedEventTimeElapsedMilliseconds(timeElapsedMilliseconds)
        )
    }

    override fun id(): String = id.value
}
