package com.usadapekora.shared.domain.auth

sealed class OAuthUserError(open val message: String? = null) {
    class NotFound(override val message: String? = null) : OAuthUserError(message = message)
    class SaveError(override val message: String? = null) : OAuthUserError(message = message)
}
