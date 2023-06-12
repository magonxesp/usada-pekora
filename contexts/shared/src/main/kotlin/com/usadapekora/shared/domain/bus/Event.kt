package com.usadapekora.shared.domain.bus

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
abstract class Event {
    val id: String = UUID.randomUUID().toString()
    abstract val name: String
    val occurredOn: Instant = Clock.System.now()
}
