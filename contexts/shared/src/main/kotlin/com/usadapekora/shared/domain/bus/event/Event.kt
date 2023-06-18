package com.usadapekora.shared.domain.bus.event

import com.usadapekora.shared.domain.getAnnotation
import kotlinx.datetime.Clock
import java.util.*

abstract class Event(
    /**
     * The event id with uuid format
     */
    open val id: String = UUID.randomUUID().toString(),
    /**
     * The date of the event was created the ISO 8601 format
     */
    open val occurredOn: String = Clock.System.now().toString()
) {
    /**
     * The event name
     */
    val name: String = getAnnotation<EventName>(this::class).name
}
