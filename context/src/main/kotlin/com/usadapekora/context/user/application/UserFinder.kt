package com.usadapekora.context.user.application

import com.usadapekora.context.user.domain.User
import com.usadapekora.context.user.domain.UserRepository

class UserFinder(private val repository: UserRepository) {

    fun findByDiscordId(discordId: String): User {
        return repository.findByDiscordId(discordId)
    }

}
