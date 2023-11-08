package com.usadapekora.auth.domain.jwt

sealed class JwtException(override val message: String? = null) : Exception(message) {
    class CodeNotFound(override val message: String? = null) : JwtException(message = message)
}
