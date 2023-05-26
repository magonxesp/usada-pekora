package com.usadapekora.auth.domain.jwt

sealed class JwtError(open val message: String? = null) {
    class CodeNotFound(override val message: String? = null) : JwtError(message = message)
}
