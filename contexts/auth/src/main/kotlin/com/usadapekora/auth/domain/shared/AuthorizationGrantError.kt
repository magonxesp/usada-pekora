package com.usadapekora.auth.domain.shared

sealed class AuthorizationGrantError(open val message: String? = null) {
    class NotFound(override val message: String? = null) : AuthorizationGrantError(message)
}
