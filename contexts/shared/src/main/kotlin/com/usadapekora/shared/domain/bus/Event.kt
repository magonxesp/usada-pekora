package com.usadapekora.shared.domain.bus

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
abstract class Event {
    /**
     * The event id
     */
    val id: String = UUID.randomUUID().toString()
    /**
     * The event name
     */
    abstract val name: String
    /**
     * The date of the event was created the ISO 8601 format
     */
    val occurredOn: String = Clock.System.now().toString()
}
