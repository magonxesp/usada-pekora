package com.usadapekora.context.application.user

import com.usadapekora.context.domain.user.User
import com.usadapekora.context.domain.user.UserRepository

class UserFinder(private val repository: UserRepository) {

    fun findByDiscordId(discordId: String): User {
        return repository.findByDiscordId(discordId)
    }

}
