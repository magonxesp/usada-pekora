package es.magonxesp.pekorabot.modules.user.application

import es.magonxesp.pekorabot.modules.user.domain.User
import es.magonxesp.pekorabot.modules.user.domain.UserRepository

class UserFinder(private val repository: UserRepository) {

    fun findByDiscordId(discordId: String): User {
        return repository.findByDiscordId(discordId)
    }

}
