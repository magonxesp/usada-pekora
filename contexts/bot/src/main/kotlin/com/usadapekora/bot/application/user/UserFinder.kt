package com.usadapekora.bot.application.user

import com.usadapekora.bot.domain.user.User
import com.usadapekora.bot.domain.user.UserRepository

class UserFinder(private val repository: UserRepository) {

    fun findByDiscordId(discordId: String): User {
        return repository.findByDiscordId(discordId)
    }

}
