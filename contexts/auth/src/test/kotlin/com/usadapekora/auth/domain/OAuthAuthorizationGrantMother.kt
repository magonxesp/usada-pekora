package com.usadapekora.auth.domain

import com.usadapekora.auth.domain.oauth.OAuthAuthorizationGrant
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

object OAuthAuthorizationGrantMother {

    fun create(
        code: String? = null,
        user: String? = null,
        expiresAt: Int? = null,
        issuedAt: Instant? = null
    ) = OAuthAuthorizationGrant.fromPrimitives(
        code = code ?: Random.instance().code.toString(),
        user = user ?: Random.instance().random.nextUUID(),
        expiresAt = expiresAt ?: 30,
        issuedAt = issuedAt ?: Clock.System.now()
    )

}
