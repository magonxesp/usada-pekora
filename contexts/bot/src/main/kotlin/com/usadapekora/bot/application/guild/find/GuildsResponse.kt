package com.usadapekora.bot.application.guild.find

import kotlinx.serialization.Serializable

@Serializable
data class GuildsResponse(
    val guilds: Array<GuildResponse>
)
