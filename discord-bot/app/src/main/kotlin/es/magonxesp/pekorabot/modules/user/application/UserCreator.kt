package es.magonxesp.pekorabot.modules.user.application

import es.magonxesp.pekorabot.modules.user.domain.User
import es.magonxesp.pekorabot.modules.user.domain.UserRepository

class UserCreator(private val repository: UserRepository) {

    fun create(id: String, discordId: String): User {
        val user = User(id, discordId)

        repository.save(user)

        return user
    }

}
