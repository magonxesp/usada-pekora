package com.usadapekora.shared

import com.usadapekora.shared.application.user.find.UserFinder
import com.usadapekora.shared.domain.LoggerFactory
import com.usadapekora.shared.domain.PersistenceTransaction
import com.usadapekora.shared.domain.auth.OAuthUserRepository
import com.usadapekora.shared.domain.bus.event.EventBus
import com.usadapekora.shared.domain.bus.event.EventConsumedRepository
import com.usadapekora.shared.domain.bus.event.EventConsumer
import com.usadapekora.shared.domain.bus.event.EventProcessedRepository
import com.usadapekora.shared.domain.user.UserRepository
import com.usadapekora.shared.domain.user.UserSessionRepository
import com.usadapekora.shared.infrastructure.Slf4jLoggerFactory
import com.usadapekora.shared.infrastructure.auth.persistence.mongodb.MongoDbOAuthUserRepository
import com.usadapekora.shared.infrastructure.bus.event.RabbitMqEventBus
import com.usadapekora.shared.infrastructure.bus.event.RabbitMqEventConsumer
import com.usadapekora.shared.infrastructure.bus.persistence.mongodb.MongoDbEventProcessedRepository
import com.usadapekora.shared.infrastructure.bus.persistence.redis.RedisEventConsumedRepository
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
    single { MongoDbEventProcessedRepository() } bind EventProcessedRepository::class
    single { RedisEventConsumedRepository() } bind EventConsumedRepository::class
    single { RabbitMqEventBus() } bind EventBus::class
    single { RabbitMqEventConsumer(get(), get(), get(), get()) } bind EventConsumer::class
    single { UserFinder(get()) }
    single { MongoDbPersistenceTransaction() } bind PersistenceTransaction::class
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
