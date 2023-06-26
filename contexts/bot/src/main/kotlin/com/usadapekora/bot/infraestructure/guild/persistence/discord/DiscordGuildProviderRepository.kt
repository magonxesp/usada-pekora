package com.usadapekora.bot.infraestructure.guild.persistence.discord

import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.guild.GuildProviderRepository
import com.usadapekora.shared.domain.user.User
import discord4j.core.DiscordClient

class DiscordGuildProviderRepository(private val token: String) : GuildProviderRepository {
    override fun findAll(userId: User.UserId): Array<Guild> {
        val client = DiscordClient.restBuilder(token)
        TODO("implement")
    }
}
