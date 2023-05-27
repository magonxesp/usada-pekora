package com.usadapekora.auth.domain

import com.usadapekora.auth.domain.jwt.Jwt
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

object JwtMother {

    fun create(
        token: String? = null,
        expiresAt: Instant? = null
    ) = Jwt.fromPrimitives(
        token = token ?: Random.instance().code.toString(),
        expiresAt = expiresAt ?: Clock.System.now()
    )

}
