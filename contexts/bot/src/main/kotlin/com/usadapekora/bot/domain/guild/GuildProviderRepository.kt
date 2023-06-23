package com.usadapekora.bot.domain.guild

import com.usadapekora.shared.domain.user.User

interface GuildProviderRepository {
    fun findAll(provider: GuildProvider, userId: User.UserId): Array<Guild>
}
