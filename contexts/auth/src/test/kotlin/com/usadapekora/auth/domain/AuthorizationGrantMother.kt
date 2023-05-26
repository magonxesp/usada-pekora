package com.usadapekora.auth.domain

import com.usadapekora.auth.domain.shared.AuthorizationGrant
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

object AuthorizationGrantMother {

    fun create(
        code: String? = null,
        user: String? = null,
        expiresAt: Int? = null,
        issuedAt: Instant? = null
    ) = AuthorizationGrant.fromPrimitives(
        code = code ?: Random.instance().code.toString(),
        user = user ?: Random.instance().random.nextUUID(),
        expiresAt = expiresAt ?: 30,
        issuedAt = issuedAt ?: Clock.System.now()
    )

}
