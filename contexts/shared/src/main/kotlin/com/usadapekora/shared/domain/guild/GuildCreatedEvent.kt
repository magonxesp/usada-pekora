package com.usadapekora.shared.domain.guild

import com.usadapekora.shared.domain.bus.event.Event
import com.usadapekora.shared.domain.bus.event.EventName

@EventName(name = "guild_created")
class GuildCreatedEvent(
    val guildId: String
) : Event()
