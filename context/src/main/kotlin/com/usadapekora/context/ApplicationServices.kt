package com.usadapekora.context

import com.usadapekora.context.application.guild.GuildPreferenceCreator
import com.usadapekora.context.application.guild.GuildPreferenceDeleter
import com.usadapekora.context.application.guild.GuildPreferencesFinder
import com.usadapekora.context.application.trigger.create.TriggerAudioCreator
import com.usadapekora.context.application.trigger.create.TriggerCreator
import com.usadapekora.context.application.trigger.find.TriggerAudioFinder
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
import com.usadapekora.context.domain.shared.file.DomainFileWriter
import com.usadapekora.context.domain.trigger.TriggerAudioRepository
import com.usadapekora.context.domain.video.ChannelSubscriber
import com.usadapekora.context.domain.video.FeedParser
import com.usadapekora.context.domain.video.VideoFeedNotifier
import com.usadapekora.context.infraestructure.discord.DiscordTextChannelVideoNotifier
import com.usadapekora.context.infraestructure.filesystem.FileSystemDomainFileWriter
import com.usadapekora.context.infraestructure.persistence.mongodb.trigger.MongoDbTriggerAudioRepository
import com.usadapekora.context.infraestructure.youtube.YoutubeFeedSubscriber
import com.usadapekora.context.infraestructure.youtube.YoutubeVideoParser
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single { RedisKeyValueCacheStorage() } bind KeyValueCacheStorage::class
    single { Sfl4jLogger() } bind Logger::class
    single { FileSystemDomainFileWriter() } bind DomainFileWriter::class
}

val triggerModule = module {
    single { MongoDbTriggerRepository() } bind TriggerRepository::class
    single { MongoDbTriggerAudioRepository() } bind TriggerAudioRepository::class
    single { TriggerMatcher() }
    single { TriggerFinder(get(), get()) }
    single { TriggerCreator(get()) }
    single { TriggerAudioCreator(get(), get()) }
    single { TriggerAudioFinder(get()) }
}

val guildModule = module {
    single { MongoDbGuildPreferencesRepository() } bind GuildPreferencesRepository::class
    single { GuildPreferenceCreator(get()) }
    single { GuildPreferenceDeleter(get()) }
    single { GuildPreferencesFinder(get()) }
}

val videoModule = module {
    single { DiscordTextChannelVideoNotifier(get()) } bind VideoFeedNotifier::class
    single { YoutubeVideoParser() } bind FeedParser::class
    single { YoutubeFeedSubscriber() } bind ChannelSubscriber::class
    single { VideoFeedParser(get()) }
    single { VideoFeedSubscriber(get()) }
    single { SendVideoFeed(get()) }
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
