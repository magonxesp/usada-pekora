package com.usadapekora.shared.domain.bus

abstract class Event(
    /**
     * The event id with uuid format
     */
    open val id: String,
    /**
     * The date of the event was created the ISO 8601 format
     */
    open val occurredOn: String
) {
    /**
     * The event name
     */
    abstract val name: String
}
