package com.usadapekora.shared.application.user.find

import arrow.core.Either
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.domain.user.UserException
import com.usadapekora.shared.domain.user.UserRepository

class UserFinder(private val repository: UserRepository) {

    fun findByDiscordId(discordId: User.UserProviderId): Either<UserException, User>
        = repository.findByDiscordId(discordId)

}
