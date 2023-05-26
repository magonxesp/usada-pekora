package com.usadapekora.auth.domain

import com.usadapekora.auth.domain.jwt.Jwt

object JwtMother {

    fun create(
        token: String? = null,
        expiresAt: Int? = null
    ) = Jwt.fromPrimitives(
        token = token ?: Random.instance().code.toString(),
        expiresAt = expiresAt ?: Jwt.JwtExpiresAt.DEFAULT_EXPIRATION_TIME
    )

}
