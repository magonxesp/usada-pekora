package com.usadapekora.bot.application.guild.find

import kotlinx.serialization.Serializable

@Serializable
data class GuildCreateRequest(
    val id: String,
    val name: String,
    val iconUrl: String,
    val providerId: String,
    val provider: String
)
