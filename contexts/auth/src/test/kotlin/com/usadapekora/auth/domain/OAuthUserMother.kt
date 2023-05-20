package com.usadapekora.auth.domain

import com.usadapekora.auth.domain.oauth.OAuthUser

object OAuthUserMother {

    fun create(id: String? = null, token: String? = null): OAuthUser = OAuthUser(
        id = id ?: Random.instance().random.nextUUID(),
        token = token ?: Random.instance().code.toString()
    )

}
