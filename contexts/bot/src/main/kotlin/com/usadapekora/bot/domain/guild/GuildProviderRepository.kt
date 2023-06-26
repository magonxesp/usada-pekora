package com.usadapekora.bot.domain.guild

import com.usadapekora.shared.domain.user.User

interface GuildProviderRepository {
    fun findAll(userId: User.UserId): Array<Guild>
}
