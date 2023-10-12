package com.usadapekora.shared.domain.bus.event

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.io.Serializable
import java.util.*

abstract class DomainEvent(
    open val id: String = UUID.randomUUID().toString(),
    open val occurredOn: Instant = Clock.System.now()
) {
    abstract val name: String
    abstract fun attributes(): Map<String, Serializable>
}
