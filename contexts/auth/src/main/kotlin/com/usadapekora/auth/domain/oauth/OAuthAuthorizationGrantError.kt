package com.usadapekora.auth.domain.oauth

sealed class OAuthAuthorizationGrantError(open val message: String? = null) {
    class NotFound(override val message: String? = null) : OAuthAuthorizationGrantError(message)
}
