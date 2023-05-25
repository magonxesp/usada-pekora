package com.usadapekora.shared

import com.usadapekora.shared.domain.user.UserRepository
import com.usadapekora.shared.infrastructure.user.peristence.mongodb.MongoDbUserRepository
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

private var koinApplication: KoinApplication? = null

val userModule = module {
    single { MongoDbUserRepository() } bind UserRepository::class
}

fun enableDependencyInjection(modules: List<Module> = listOf()) {
    koinApplication = startKoin {
        modules(listOf(userModule).plus(modules))
    }
}

fun serviceContainer(): Koin
    = koinApplication?.koin ?: throw RuntimeException("The Koin dependency injection is not enabled")
