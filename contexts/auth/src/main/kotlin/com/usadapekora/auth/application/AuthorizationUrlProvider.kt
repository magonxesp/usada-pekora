package com.usadapekora.auth.application

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.auth.domain.OAuthProvider
import com.usadapekora.auth.domain.OAuthProviderError
import com.usadapekora.auth.domain.OAuthProviderFactory

class AuthorizationUrlProvider {

    fun getUrl(provider: String): Either<OAuthProviderError.NotAvailable, String> {
        val providerEnum = Either.catch { OAuthProvider.fromValue(provider) }.let {
            if (it.isLeft()) return OAuthProviderError.NotAvailable("The $provider provider is not available").left()
            it.getOrNull()!!
        }

        return OAuthProviderFactory.get(providerEnum).let {
            if (it.isLeft()) return it.leftOrNull()!!.left()
            it.getOrNull()!!.authorizeUrl().right()
        }
    }

}
