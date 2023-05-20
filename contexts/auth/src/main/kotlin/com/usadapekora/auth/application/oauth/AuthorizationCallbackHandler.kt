package com.usadapekora.auth.application.oauth

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.auth.domain.oauth.OAuthProvider
import com.usadapekora.auth.domain.oauth.OAuthProviderError
import com.usadapekora.auth.domain.oauth.OAuthProviderFactory

class AuthorizationCallbackHandler(private val providerFactory: OAuthProviderFactory) {

    suspend fun handle(provider: String, code: String): Either<OAuthProviderError.CallbackError, Unit> {
        val providerEnum = Either.catch { OAuthProvider.fromValue(provider) }.let {
            if (it.isLeft()) return OAuthProviderError.CallbackError("The $provider provider is not available").left()
            it.getOrNull()!!
        }

        val providerInstance = providerFactory.getInstance(providerEnum).let {
            if (it.isLeft()) return OAuthProviderError.CallbackError("The $provider provider is not available").left()
            it.getOrNull()!!
        }

        val authenticatedUser = providerInstance.handleCallback(code).let {
            if (it.isLeft()) return it.leftOrNull()!!.left()
            it.getOrNull()!!
        }

        return Unit.right()
    }

}
