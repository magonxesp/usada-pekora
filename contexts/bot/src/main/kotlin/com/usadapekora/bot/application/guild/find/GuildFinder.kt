package com.usadapekora.bot.application.guild.find

import com.usadapekora.bot.domain.guild.GuildRepository
import com.usadapekora.shared.domain.user.User

class GuildFinder(private val repository: GuildRepository) {
    fun findUserGuilds(userId: User.UserId): GuildsResponse =
        repository.findByUserId(userId)
            .map { GuildResponse.fromEntity(it) }
            .toTypedArray()
            .let { GuildsResponse(guilds = it) }
}
