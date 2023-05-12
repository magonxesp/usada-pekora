package com.usadapekora.bot.application.user

import arrow.core.Either
import com.usadapekora.bot.domain.user.User
import com.usadapekora.bot.domain.user.UserException
import com.usadapekora.bot.domain.user.UserRepository

class UserFinder(private val repository: UserRepository) {

    fun findByDiscordId(discordId: User.DiscordUserId): Either<UserException, User>
        = repository.findByDiscordId(discordId)

}
