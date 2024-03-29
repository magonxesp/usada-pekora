package com.usadapekora.shared.domain.guild

import com.usadapekora.shared.domain.bus.event.DomainEvent
import com.usadapekora.shared.domain.bus.event.DomainEventFactory
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.io.Serializable
import java.util.*

class GuildCreatedEvent(
    override val id: String = UUID.randomUUID().toString(),
    override val occurredOn: Instant = Clock.System.now(),
    val guildId: String
) : DomainEvent(id, occurredOn) {
    override val name: String = "guild.created"

    override fun attributes(): Map<String, Serializable> = mapOf(
        "guildId" to guildId
    )

    companion object : DomainEventFactory<GuildCreatedEvent> {
        override fun fromPrimitives(
            id: String,
            occurredOn: Instant,
            name: String,
            attributes: Map<String, Serializable>
        ): GuildCreatedEvent = GuildCreatedEvent(
            id = id,
            occurredOn = occurredOn,
            guildId = attributes["guildId"]!! as String
        )
    }
}
