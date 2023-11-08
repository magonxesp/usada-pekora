package com.usadapekora.auth.domain.shared

sealed class AuthorizationGrantException(override val message: String? = null) : Exception(message) {
    class NotFound(override val message: String? = null) : AuthorizationGrantException(message)
}
