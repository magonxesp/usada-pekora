package com.usadapekora.auth.domain

sealed class OAuthProviderError(open val message: String? = null) {
    class NotAvailable(override val message: String? = null) : OAuthProviderError(message = message)
}
