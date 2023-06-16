package com.usadapekora.auth

import com.usadapekora.auth.application.jwt.AccessJwtIssuer
import com.usadapekora.auth.application.jwt.SignatureJwkIssuer
import com.usadapekora.auth.application.oauth.OAuthAuthorizationProviderAuthorizationHandler
import com.usadapekora.auth.application.oauth.OAuthAuthorizationProviderAuthorizeUrlFactory
import com.usadapekora.auth.domain.jwt.JwkIssuer
import com.usadapekora.auth.domain.jwt.JwtIssuer
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationGrantCodeCreator
import com.usadapekora.auth.domain.shared.AuthorizationGrantRepository
import com.usadapekora.auth.domain.oauth.OAuthProviderFactory
import com.usadapekora.auth.infrastructure.jwt.auth0.Auth0JwkIssuer
import com.usadapekora.auth.infrastructure.jwt.auth0.Auth0JwtIssuer
import com.usadapekora.auth.infrastructure.oauth.discord.DiscordOAuthProvider
import com.usadapekora.auth.infrastructure.oauth.jakarta.JakartaOAuthAuthorizationGrantCodeCreator
import com.usadapekora.auth.infrastructure.oauth.koin.KoinOAuthProviderFactory
import com.usadapekora.auth.infrastructure.shared.persistence.redis.RedisAuthorizationGrantRepository
import com.usadapekora.shared.domain.bus.EventBus
import com.usadapekora.shared.infrastructure.bus.RabbitMqEventBus
import kotlinx.datetime.Clock
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    factory { DiscordOAuthProvider() }
    single { Clock.System } bind Clock::class
    single { KoinOAuthProviderFactory() } bind OAuthProviderFactory::class
    single { JakartaOAuthAuthorizationGrantCodeCreator() } bind OAuthAuthorizationGrantCodeCreator::class
    single { RedisAuthorizationGrantRepository() } bind AuthorizationGrantRepository::class
    single { Auth0JwtIssuer(get()) } bind JwtIssuer::class
    single { Auth0JwkIssuer(get()) } bind JwkIssuer::class
    single { AccessJwtIssuer(get(), get()) }
    single { OAuthAuthorizationProviderAuthorizeUrlFactory(get()) }
    single { OAuthAuthorizationProviderAuthorizationHandler(get(), get(), get(), get(), get(), get(), get()) }
    single { SignatureJwkIssuer(get()) }
}

val modules = listOf(
    authModule,
)
