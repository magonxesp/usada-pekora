package com.usadapekora.shared.domain.bus.event

import kotlinx.datetime.Clock
import java.util.UUID

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
    abstract val name: String
}
