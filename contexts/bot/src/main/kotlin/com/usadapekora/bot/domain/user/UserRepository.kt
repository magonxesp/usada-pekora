package com.usadapekora.bot.domain.user

import arrow.core.Either

interface UserRepository {
    fun find(id: User.UserId): Either<UserException.NotFound, User>
    fun findByDiscordId(discordId: User.DiscordUserId): Either<UserException.NotFound, User>
    fun save(entity: User)
    fun delete(entity: User)
}
