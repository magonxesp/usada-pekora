package com.usadapekora.auth

import com.usadapekora.auth.application.oauth.AuthorizationCallbackHandler
import com.usadapekora.auth.application.oauth.AuthorizationUrlProvider
import com.usadapekora.auth.domain.oauth.OAuthProviderFactory
import com.usadapekora.auth.infrastructure.oauth.OAuthProviderFactoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    single { OAuthProviderFactoryImpl() } bind OAuthProviderFactory::class
    single { AuthorizationUrlProvider(get()) }
    single { AuthorizationCallbackHandler(get()) }
}

val modules = listOf(
    authModule,
)
