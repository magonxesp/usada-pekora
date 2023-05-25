package com.usadapekora.auth

import com.usadapekora.auth.application.oauth.OAuthAuthorizationProviderAuthorizationHandler
import com.usadapekora.auth.application.oauth.OAuthAuthorizationProviderAuthorizeUrlFactory
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationGrantCodeCreator
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationGrantRepository
import com.usadapekora.auth.domain.oauth.OAuthProviderFactory
import com.usadapekora.auth.infrastructure.oauth.discord.DiscordOAuthProvider
import com.usadapekora.auth.infrastructure.oauth.jakarta.JakartaOAuthAuthorizationGrantCodeCreator
import com.usadapekora.auth.infrastructure.oauth.koin.KoinOAuthProviderFactory
import com.usadapekora.auth.infrastructure.oauth.persistence.redis.RedisOAuthAuthorizationGrantRepository
import kotlinx.datetime.Clock
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    factory { DiscordOAuthProvider() }
    single { KoinOAuthProviderFactory() } bind OAuthProviderFactory::class
    single { JakartaOAuthAuthorizationGrantCodeCreator() } bind OAuthAuthorizationGrantCodeCreator::class
    single { RedisOAuthAuthorizationGrantRepository() } bind OAuthAuthorizationGrantRepository::class
    single { Clock.System } bind Clock::class
    single { OAuthAuthorizationProviderAuthorizeUrlFactory(get()) }
    single { OAuthAuthorizationProviderAuthorizationHandler(get(), get(), get(), get(), get()) }
}

val modules = listOf(
    authModule,
)
