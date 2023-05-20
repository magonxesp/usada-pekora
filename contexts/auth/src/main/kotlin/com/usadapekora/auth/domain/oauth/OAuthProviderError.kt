package com.usadapekora.auth.domain.oauth

sealed class OAuthProviderError(open val message: String? = null) {
    class NotAvailable(override val message: String? = null) : OAuthProviderError(message = message)
    class CallbackError(override val message: String? = null) : OAuthProviderError(message = message)
}
