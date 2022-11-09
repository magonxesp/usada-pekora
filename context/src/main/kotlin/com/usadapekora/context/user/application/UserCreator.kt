package com.usadapekora.context.user.application

import com.usadapekora.context.user.domain.User
import com.usadapekora.context.user.domain.UserRepository

class UserCreator(private val repository: UserRepository) {

    fun create(id: String, discordId: String): User {
        val user = User(id, discordId)

        repository.save(user)

        return user
    }

}
