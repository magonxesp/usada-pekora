package com.usadapekora.bot.infraestructure.guild.persistence.discord

import kotlinx.serialization.Serializable

@Serializable
data class DiscordGuild(
    val id: String,
    val name: String,
    val icon: String?,
    val owner: Boolean,
    val permissions: String,
    val features: List<String>
)
