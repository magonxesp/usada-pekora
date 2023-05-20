package com.usadapekora.auth.domain.oauth

import arrow.core.Either
import com.usadapekora.auth.domain.shared.AuthenticatedUser

interface OAuthAuthorizationProvider {
    fun authorizeUrl(): String
    suspend fun handleCallback(code: String): Either<OAuthProviderError.CallbackError, OAuthUser>
}
