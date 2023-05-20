package com.usadapekora.auth.domain

import com.usadapekora.auth.domain.shared.AuthenticatedUser

object AuthenticatedUserMother {

    fun create(
        id: String? = null,
        avatarUrl: String? = null,
        name: String? = null
    ) = AuthenticatedUser.fromPrimitives(
        id = id ?: Random.instance().random.nextUUID(),
        avatarUrl = avatarUrl ?: Random.instance().internet.domain(),
        name = name ?: Random.instance().overwatch.heroes()
    )

}
