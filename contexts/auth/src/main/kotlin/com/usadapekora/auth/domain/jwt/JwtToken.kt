package com.usadapekora.auth.domain.jwt

data class JwtToken(
    val token: JwtTokenToken,
    val expiresAt: JwtTokenExpiresAt
) {
    data class JwtTokenToken(val value: String)
    /**
     * Token expiration in seconds
     */
    data class JwtTokenExpiresAt(val value: Int) {
        companion object {
            const val DEFAULT_EXPIRATION_TIME = 604800 // 7 days
        }
    }

    companion object {
        fun fromPrimitives(
            token: String,
            expiresAt: Int
        ) = JwtToken(
            token = JwtTokenToken(token),
            expiresAt = JwtTokenExpiresAt(expiresAt)
        )
    }
}
