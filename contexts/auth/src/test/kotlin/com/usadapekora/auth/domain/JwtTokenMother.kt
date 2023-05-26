package com.usadapekora.auth.domain

import com.usadapekora.auth.domain.jwt.JwtToken

object JwtTokenMother {

    fun create(
        token: String? = null,
        expiresAt: Int? = null
    ) = JwtToken.fromPrimitives(
        token = token ?: Random.instance().code.toString(),
        expiresAt = expiresAt ?: JwtToken.JwtTokenExpiresAt.DEFAULT_EXPIRATION_TIME
    )

}
