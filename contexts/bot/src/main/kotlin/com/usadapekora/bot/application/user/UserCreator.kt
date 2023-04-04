package com.usadapekora.bot.application.user

import com.usadapekora.bot.domain.user.User
import com.usadapekora.bot.domain.user.UserRepository

class UserCreator(private val repository: UserRepository) {

    fun create(id: String, discordId: String): User {
        val user = User(id, discordId)

        repository.save(user)

        return user
    }

}
