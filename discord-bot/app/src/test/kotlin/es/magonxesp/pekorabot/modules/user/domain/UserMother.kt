package es.magonxesp.pekorabot.modules.user.domain

import java.util.Random
import java.util.UUID

class UserMother {
    companion object {
        fun create(id: String? = null, discordId: String? = null) = User(
            id = id ?: UUID.randomUUID().toString(),
            discordId = discordId ?: Random().nextLong().toString()
        )
    }
}
