package com.usadapekora.context.domain

import com.usadapekora.context.domain.user.User
import java.util.Random
import java.util.UUID

object UserMother : ObjectMother<User> {
    fun create(id: String? = null, discordId: String? = null) = User(
        id = id ?: UUID.randomUUID().toString(),
        discordId = discordId ?: Random().nextLong().toString()
    )

    override fun random(): User = create()
}
