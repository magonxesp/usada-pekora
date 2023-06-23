package com.usadapekora.auth.domain

import com.usadapekora.auth.domain.oauth.OAuthProvider
import com.usadapekora.shared.domain.auth.OAuthUser

object OAuthUserMother {

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
        provider = provider ?: Random.instance().random.nextEnum(OAuthProvider::class.java).value,
        token = token ?: Random.instance().code.toString(),
        userId = nextDomainUserId ?: Random.instance().random.nextUUID(),
    )

}
