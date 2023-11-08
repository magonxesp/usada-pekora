package com.usadapekora.auth.domain.oauth

import arrow.core.Either

interface OAuthProviderFactory {
    fun getInstance(provider: OAuthProvider): Either<OAuthProviderException.NotAvailable, OAuthAuthorizationProvider>
}
