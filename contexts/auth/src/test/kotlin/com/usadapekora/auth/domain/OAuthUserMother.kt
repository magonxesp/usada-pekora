package com.usadapekora.auth.domain

import com.usadapekora.auth.domain.oauth.OAuthUser

object OAuthUserMother {

    fun create(
        id: String? = null,
        name: String? = null,
        avatar: String? = null,
        token: String? = null,
        nextDomainUserId: String? = null
    ): OAuthUser = OAuthUser(
        id = id ?: Random.instance().random.nextUUID(),
        name = name ?: Random.instance().overwatch.heroes(),
        avatar = avatar ?: Random.instance().internet.domain(),
        token = token ?: Random.instance().code.toString(),
        nextDomainUserId = nextDomainUserId ?: Random.instance().random.nextUUID(),
    )

}
