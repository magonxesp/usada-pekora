package com.usadapekora.shared.domain.bus.event

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.io.Serializable
import java.util.*

data class ExampleDomainEvent(
    override val id: String = UUID.randomUUID().toString(),
    override val occurredOn: Instant = Clock.System.now(),
    val userId: String = UUID.randomUUID().toString(),
    val guildId: String = UUID.randomUUID().toString()
) : DomainEvent(id, occurredOn) {
    override val name: String = "example_event"
    override fun attributes(): Map<String, Serializable> = mapOf(
        "userId" to userId,
        "guildId" to guildId
    )

    companion object : DomainEventFactory<ExampleDomainEvent> {
        override fun fromPrimitives(
            id: String,
            occurredOn: Instant,
            name: String,
            attributes: Map<String, Serializable>
        ): ExampleDomainEvent = ExampleDomainEvent(
            id = id,
            occurredOn = occurredOn,
            userId = attributes["userId"]!! as String,
            guildId = attributes["guildId"]!! as String
        )
    }
}
