package com.usadapekora.bot.application.guild.find

import com.usadapekora.bot.domain.guild.Guild
import kotlinx.serialization.Serializable

@Serializable
data class GuildResponse(
    val id: String,
    val name: String,
    val iconUrl: String,
    val providerId: String,
    val provider: String
) {
    companion object {
        fun fromEntity(guild: Guild) = GuildResponse(
            id = guild.id.value,
            name = guild.name.value,
            iconUrl = guild.iconUrl.value,
            providerId = guild.providerId.value,
            provider = guild.provider.value
        )
    }
}
