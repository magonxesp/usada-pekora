package com.usadapekora.context

import com.usadapekora.context.application.guild.GuildPreferenceCreator
import com.usadapekora.context.application.guild.GuildPreferenceDeleter
import com.usadapekora.context.application.guild.GuildPreferencesFinder
import com.usadapekora.context.domain.guild.GuildPreferencesRepository
import com.usadapekora.context.infraestructure.persistence.mongodb.guild.MongoDbGuildPreferencesRepository
import com.usadapekora.context.domain.shared.KeyValueCacheStorage
import com.usadapekora.context.domain.shared.Logger
import com.usadapekora.context.infraestructure.cache.RedisKeyValueCacheStorage
import com.usadapekora.context.infraestructure.logger.Sfl4jLogger
import com.usadapekora.context.application.trigger.find.TriggerFinder
import com.usadapekora.context.domain.trigger.TriggerMatcher
import com.usadapekora.context.domain.trigger.TriggerRepository
import com.usadapekora.context.infraestructure.persistence.mongodb.trigger.MongoDbTriggerRepository
import com.usadapekora.context.application.video.SendVideoFeed
import com.usadapekora.context.application.video.VideoFeedParser
import com.usadapekora.context.application.video.VideoFeedSubscriber
import com.usadapekora.context.domain.video.ChannelSubscriber
import com.usadapekora.context.domain.video.FeedParser
import com.usadapekora.context.domain.video.VideoFeedNotifier
import com.usadapekora.context.infraestructure.discord.DiscordTextChannelVideoNotifier
import com.usadapekora.context.infraestructure.youtube.YoutubeFeedSubscriber
import com.usadapekora.context.infraestructure.youtube.YoutubeVideoParser
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    factory { RedisKeyValueCacheStorage() } bind KeyValueCacheStorage::class
    factory { Sfl4jLogger() } bind Logger::class
}

val triggerModule = module {
    factory { MongoDbTriggerRepository() } bind TriggerRepository::class
    factory { TriggerMatcher() }
    factory { TriggerFinder(get(), get()) }
}

val guildModule = module {
    factory { MongoDbGuildPreferencesRepository() } bind GuildPreferencesRepository::class
    factory { GuildPreferenceCreator(get()) }
    factory { GuildPreferenceDeleter(get()) }
    factory { GuildPreferencesFinder(get()) }
}

val videoModule = module {
    factory { DiscordTextChannelVideoNotifier(get()) } bind VideoFeedNotifier::class
    factory { YoutubeVideoParser() } bind FeedParser::class
    factory { YoutubeFeedSubscriber() } bind ChannelSubscriber::class
    factory { VideoFeedParser(get()) }
    factory { VideoFeedSubscriber(get()) }
    factory { SendVideoFeed(get()) }
}

fun enableDependencyInjection(extraModules: List<Module> = listOf()) {
    startKoin {
        modules(
            listOf(
                sharedModule,
                triggerModule,
                guildModule,
                videoModule
            ).plus(extraModules)
        )
    }
}
