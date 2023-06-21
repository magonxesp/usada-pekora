package com.usadapekora.auth.domain.oauth

import arrow.core.Either

interface OAuthAuthorizationProvider {
    fun authorizeUrl(): String
    suspend fun handleCallback(code: String): Either<OAuthProviderError.CallbackError, OAuthUser>
}
