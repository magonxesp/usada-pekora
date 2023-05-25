package com.usadapekora.auth.infrastructure.oauth.koin

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationProvider
import com.usadapekora.auth.domain.oauth.OAuthProvider
import com.usadapekora.auth.domain.oauth.OAuthProviderError
import com.usadapekora.auth.domain.oauth.OAuthProviderFactory
import com.usadapekora.auth.infrastructure.oauth.discord.DiscordOAuthProvider
import com.usadapekora.shared.serviceContainer

private val providers = mapOf(
    OAuthProvider.DISCORD to DiscordOAuthProvider::class
)

class KoinOAuthProviderFactory : OAuthProviderFactory {

    override fun getInstance(provider: OAuthProvider): Either<OAuthProviderError.NotAvailable, OAuthAuthorizationProvider> {
        val providerClass = providers[provider] ?: return OAuthProviderError.NotAvailable("The ${provider.value} OAuth provider is not available").left()
        return serviceContainer().get<OAuthAuthorizationProvider>(providerClass).right()
    }

}
