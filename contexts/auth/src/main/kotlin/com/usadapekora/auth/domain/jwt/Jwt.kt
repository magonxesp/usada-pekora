package com.usadapekora.auth.domain.jwt

import kotlinx.datetime.Instant

data class Jwt(
    val token: JwtToken,
    val expiresAt: JwtExpiresAt
) {
    data class JwtToken(val value: String)
    data class JwtExpiresAt(val value: Instant) {
        companion object {
            const val DEFAULT_EXPIRATION_TIME = 604800 // 7 days
        }
    }

    companion object {
        fun fromPrimitives(
            token: String,
            expiresAt: Instant
        ) = Jwt(
            token = JwtToken(token),
            expiresAt = JwtExpiresAt(expiresAt)
        )
    }
}
