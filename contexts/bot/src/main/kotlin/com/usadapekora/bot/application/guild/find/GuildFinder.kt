package com.usadapekora.bot.application.guild.find

import arrow.core.Either
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.guild.GuildException
import com.usadapekora.bot.domain.guild.GuildProvider
import com.usadapekora.bot.domain.guild.GuildRepository
import com.usadapekora.shared.domain.user.User

class GuildFinder(private val repository: GuildRepository) {
    fun findUserGuilds(userId: User.UserId): GuildsResponse =
        repository.findByUserId(userId)
            .map { GuildResponse.fromEntity(it) }
            .toTypedArray()
            .let { GuildsResponse(guilds = it) }

    fun findByProviderId(id: String, provider: String): Either<GuildException.NotFound, Guild>
        = repository.findByProvider(Guild.GuildProviderId(id), GuildProvider.fromValue(provider))
}
