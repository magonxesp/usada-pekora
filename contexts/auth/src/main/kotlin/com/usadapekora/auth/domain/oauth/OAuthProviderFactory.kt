package com.usadapekora.auth.domain.oauth

import arrow.core.Either

interface OAuthProviderFactory {
    fun getInstance(provider: OAuthProvider): Either<OAuthProviderError.NotAvailable, OAuthAuthorizationProvider>
}
