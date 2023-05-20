package com.usadapekora.auth.infrastructure.oauth

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationProvider
import com.usadapekora.auth.domain.oauth.OAuthProvider
import com.usadapekora.auth.domain.oauth.OAuthProviderError
import com.usadapekora.auth.domain.oauth.OAuthProviderFactory
import com.usadapekora.auth.infrastructure.oauth.discord.DiscordOAuthProvider
import kotlin.reflect.full.createInstance

private val providers = mapOf(
    OAuthProvider.DISCORD to DiscordOAuthProvider::class
)

class OAuthProviderFactoryImpl : OAuthProviderFactory {

    override fun getInstance(provider: OAuthProvider): Either<OAuthProviderError.NotAvailable, OAuthAuthorizationProvider>
        = providers[provider]?.createInstance().let {
            it?.right() ?: OAuthProviderError.NotAvailable("The ${provider.value} OAuth provider is not available").left()
        }

}
