package com.usadapekora.shared.domain

import com.usadapekora.shared.domain.user.User
import kotlin.random.Random as KotlinRandom

object UserMother : ObjectMother<User> {
    fun create(
        id: String? = null,
        avatar: String? = null,
        name: String? = null,
        providerId: String? = null,
        provider: String? = null
    ) = User.fromPrimitives(
        id = id ?: Random.instance().random.nextUUID(),
        avatar = avatar ?: Random.instance().internet.domain(),
        name = name ?: Random.instance().overwatch.heroes(),
        providerId = providerId ?: KotlinRandom.nextLong().toString(),
        provider = provider ?: Random.instance().random.nextEnum(User.UserProvider::class.java).value
    )

    override fun random(): User = create()
}
