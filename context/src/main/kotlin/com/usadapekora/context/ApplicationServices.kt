package com.usadapekora.context

import com.usadapekora.context.guild.application.GuildPreferenceCreator
import com.usadapekora.context.guild.application.GuildPreferenceDeleter
import com.usadapekora.context.guild.application.GuildPreferencesFinder
import com.usadapekora.context.guild.domain.GuildPreferencesRepository
import com.usadapekora.context.guild.infraestructure.persistence.MongoDbGuildPreferencesRepository
import com.usadapekora.context.shared.domain.KeyValueCacheStorage
import com.usadapekora.context.shared.domain.Logger
import com.usadapekora.context.shared.infraestructure.cache.RedisKeyValueCacheStorage
import com.usadapekora.context.shared.infraestructure.logger.Sfl4jLogger
import com.usadapekora.context.trigger.application.TriggerFinder
import com.usadapekora.context.trigger.domain.TriggerMatcher
import com.usadapekora.context.trigger.domain.TriggerRepository
import com.usadapekora.context.trigger.infraestructure.persistence.mongodb.MongoDbTriggerRepository
import com.usadapekora.context.trigger.infraestructure.persistence.strapi.StrapiTriggerRepository
import com.usadapekora.context.video.application.SendVideoFeed
import com.usadapekora.context.video.application.VideoFeedParser
import com.usadapekora.context.video.application.VideoFeedSubscriber
import com.usadapekora.context.video.domain.ChannelSubscriber
import com.usadapekora.context.video.domain.FeedParser
import com.usadapekora.context.video.domain.VideoFeedNotifier
import com.usadapekora.context.video.infraestructure.discord.DiscordTextChannelVideoNotifier
import com.usadapekora.context.video.infraestructure.youtube.YoutubeFeedSubscriber
import com.usadapekora.context.video.infraestructure.youtube.YoutubeVideoParser
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
