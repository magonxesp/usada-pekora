package com.usadapekora.shared.domain

import com.usadapekora.shared.domain.auth.OAuthUser

object OAuthUserMother : ObjectMother<OAuthUser> {

    fun create(
        id: String? = null,
        name: String? = null,
        avatar: String? = null,
        token: String? = null,
        provider: String? = null,
        nextDomainUserId: String? = null
    ): OAuthUser = OAuthUser(
        id = id ?: Random.instance().random.nextUUID(),
        name = name ?: Random.instance().overwatch.heroes(),
        avatar = avatar ?: Random.instance().internet.domain(),
        provider = provider ?: Random.instance().random.randomValue(arrayOf("discord")),
        token = token ?: Random.instance().code.toString(),
        userId = nextDomainUserId ?: Random.instance().random.nextUUID(),
    )

    override fun random() = create()
}
