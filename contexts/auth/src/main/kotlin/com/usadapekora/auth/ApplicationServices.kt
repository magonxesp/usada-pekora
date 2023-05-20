package com.usadapekora.auth

import com.usadapekora.auth.application.AuthorizationUrlProvider
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    single { AuthorizationUrlProvider() } bind AuthorizationUrlProvider::class
}

val modules = listOf(
    authModule,
)
