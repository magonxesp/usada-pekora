package com.usadapekora.shared.domain.auth

sealed class OAuthUserException(override val message: String? = null) : Exception(message) {
    class NotFound(override val message: String? = null) : OAuthUserException(message = message)
    class SaveError(override val message: String? = null) : OAuthUserException(message = message)
}
