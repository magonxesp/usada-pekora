package com.usadapekora.shared

import com.usadapekora.shared.application.user.find.UserFinder
import com.usadapekora.shared.domain.LoggerFactory
import com.usadapekora.shared.domain.PersistenceTransaction
import com.usadapekora.shared.domain.auth.OAuthUserRepository
import com.usadapekora.shared.domain.bus.command.CommandBus
import com.usadapekora.shared.domain.bus.event.DomainEventBus
import com.usadapekora.shared.domain.bus.event.DomainEventConsumer
import com.usadapekora.shared.domain.user.UserRepository
import com.usadapekora.shared.domain.user.UserSessionRepository
import com.usadapekora.shared.infrastructure.Slf4jLoggerFactory
import com.usadapekora.shared.infrastructure.auth.persistence.mongodb.MongoDbOAuthUserRepository
import com.usadapekora.shared.infrastructure.bus.command.*
import com.usadapekora.shared.infrastructure.bus.event.*
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbPersistenceTransaction
import com.usadapekora.shared.infrastructure.user.persistence.mongodb.MongoDbUserRepository
import com.usadapekora.shared.infrastructure.user.persistence.mongodb.MongoDbUserSessionRepository
import kotlinx.datetime.Clock
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

private var koinApplication: KoinApplication? = null

val sharedModule = module {
    single { Clock.System } bind Clock::class
    single { Slf4jLoggerFactory() } bind LoggerFactory::class
    single { MongoDbUserRepository() } bind UserRepository::class
    single { MongoDbUserSessionRepository() } bind UserSessionRepository::class
    single { MongoDbOAuthUserRepository() } bind OAuthUserRepository::class
    single { DomainEventRegistry() }
    single { DomainEventSerializer() }
    single { DomainEventDeserializer(get()) }
    single { DomainEventSubscriberDispatcher(get(), get()) }
    single { RabbitMqEventBus(get(), get()) } bind DomainEventBus::class
    single { RabbitMqEventConsumer(get(), get(), get(), get()) } bind DomainEventConsumer::class
    single { UserFinder(get()) }
    single { MongoDbPersistenceTransaction() } bind PersistenceTransaction::class
    single { CommandRegistry() }
    single { CommandSerializer() }
    single { CommandDeserializer(get()) }
    single { CommandDispatcher(get(), get()) }
    single { InMemoryCommandBus(get()) } bind CommandBus::class
}

fun enableDependencyInjection(modules: List<Module> = listOf()) {
    if (GlobalContext.getOrNull() == null) {
        koinApplication = startKoin {
            modules(listOf(sharedModule).plus(modules))
        }
    }
}

fun serviceContainer(): Koin
    = koinApplication?.koin ?: throw RuntimeException("The Koin dependency injection is not enabled")
