package com.usadapekora.bot.infraestructure.guild.persistence.discord

import com.usadapekora.bot.discordBotToken
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.guild.GuildProvider
import com.usadapekora.bot.domain.guild.GuildProviderRepository
import com.usadapekora.shared.domain.user.User
import discord4j.core.DiscordClient

class DiscordGuildProviderRepository() : GuildProviderRepository {
    override fun findAll(provider: GuildProvider, userId: User.UserId): Array<Guild> {
        val client = DiscordClient.restBuilder(discordBotToken)
        // TODO("implement")
    }
}
