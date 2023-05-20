package com.usadapekora.auth.domain.oauth

sealed class OAuthProviderException(override val message: String? = null) : Exception(message) {
    class NotFound(override val message: String? = null) : OAuthProviderException(message = message)
}