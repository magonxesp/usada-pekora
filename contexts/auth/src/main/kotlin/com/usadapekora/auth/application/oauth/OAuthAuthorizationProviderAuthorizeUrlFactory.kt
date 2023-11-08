package com.usadapekora.auth.application.oauth

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.auth.domain.oauth.OAuthProvider
import com.usadapekora.auth.domain.oauth.OAuthProviderException
import com.usadapekora.auth.domain.oauth.OAuthProviderFactory

class OAuthAuthorizationProviderAuthorizeUrlFactory(private val providerFactory: OAuthProviderFactory) {

    fun getUrl(provider: String): Either<OAuthProviderException.NotAvailable, String> {
        val providerEnum = Either.catch { OAuthProvider.fromValue(provider) }.let {
            if (it.isLeft()) return OAuthProviderException.NotAvailable("The $provider provider is not available").left()
            it.getOrNull()!!
        }

        return providerFactory.getInstance(providerEnum).let {
            if (it.isLeft()) return it.leftOrNull()!!.left()
            it.getOrNull()!!.authorizeUrl().right()
        }
    }

}
