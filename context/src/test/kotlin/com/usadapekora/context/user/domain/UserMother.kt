package com.usadapekora.context.user.domain

import java.util.Random
import java.util.UUID

object UserMother {
    fun create(id: String? = null, discordId: String? = null) = User(
        id = id ?: UUID.randomUUID().toString(),
        discordId = discordId ?: Random().nextLong().toString()
    )
}
