package com.usadapekora.auth.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.auth.domain.providers.DiscordOAuthProvider
import kotlin.reflect.full.createInstance

private val providers = mapOf(
    OAuthProvider.DISCORD to DiscordOAuthProvider::class
)

object OAuthProviderFactory {

    fun get(provider: OAuthProvider): Either<OAuthProviderError.NotAvailable, OAuthAuthorizationProvider>
        = providers[provider]?.createInstance().let {
            it?.right() ?: OAuthProviderError.NotAvailable("The ${provider.value} OAuth provider is not available").left()
        }

}
