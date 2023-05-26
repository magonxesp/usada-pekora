package com.usadapekora.auth.domain.jwt

data class Jwt(
    val token: JwtToken,
    val expiresAt: JwtExpiresAt
) {
    data class JwtToken(val value: String)
    /**
     * Token expiration in seconds
     */
    data class JwtExpiresAt(val value: Int) {
        companion object {
            const val DEFAULT_EXPIRATION_TIME = 604800 // 7 days
        }
    }

    companion object {
        fun fromPrimitives(
            token: String,
            expiresAt: Int
        ) = Jwt(
            token = JwtToken(token),
            expiresAt = JwtExpiresAt(expiresAt)
        )
    }
}
