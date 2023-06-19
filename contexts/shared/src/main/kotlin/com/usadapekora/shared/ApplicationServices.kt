package com.usadapekora.shared

import com.usadapekora.shared.domain.Logger
import com.usadapekora.shared.domain.bus.event.EventBus
import com.usadapekora.shared.domain.bus.event.EventConsumedRepository
import com.usadapekora.shared.domain.bus.event.EventConsumer
import com.usadapekora.shared.domain.bus.event.EventProcessedRepository
import com.usadapekora.shared.domain.user.UserRepository
import com.usadapekora.shared.infrastructure.Sfl4jLogger
import com.usadapekora.shared.infrastructure.bus.event.RabbitMqEventBus
import com.usadapekora.shared.infrastructure.bus.event.RabbitMqEventConsumer
import com.usadapekora.shared.infrastructure.bus.event.persistence.mongodb.MongoDbEventProcessedRepository
import com.usadapekora.shared.infrastructure.bus.event.persistence.redis.RedisEventConsumedRepository
import com.usadapekora.shared.infrastructure.user.peristence.mongodb.MongoDbUserRepository
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

private var koinApplication: KoinApplication? = null

val sharedModule = module {
    single { Sfl4jLogger() } bind Logger::class
    single { MongoDbUserRepository() } bind UserRepository::class
    single { MongoDbEventProcessedRepository() } bind EventProcessedRepository::class
    single { RedisEventConsumedRepository() } bind EventConsumedRepository::class
    single { RabbitMqEventBus() } bind EventBus::class
    single { RabbitMqEventConsumer(get(), get(), get()) } bind EventConsumer::class

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
