package com.usadapekora.context.user.domain

import com.usadapekora.context.user.domain.User
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
