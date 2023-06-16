package com.usadapekora.bot.application.guild.create

import kotlinx.serialization.Serializable

@Serializable
data class GuildMemberCreateRequest(
    val userId: String,
    val guildId: String
)
