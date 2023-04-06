package com.usadapekora.bot

import com.usadapekora.bot.application.guild.GuildPreferenceCreator
import com.usadapekora.bot.application.guild.GuildPreferenceDeleter
import com.usadapekora.bot.application.guild.GuildPreferencesFinder
import com.usadapekora.bot.application.trigger.create.TriggerAudioCreator
import com.usadapekora.bot.application.trigger.create.TriggerCreator
import com.usadapekora.bot.application.trigger.delete.TriggerAudioDeleter
import com.usadapekora.bot.application.trigger.delete.TriggerDeleter
import com.usadapekora.bot.application.trigger.find.TriggerAudioFinder
import com.usadapekora.bot.domain.guild.GuildPreferencesRepository
import com.usadapekora.bot.infraestructure.persistence.mongodb.guild.MongoDbGuildPreferencesRepository
import com.usadapekora.bot.domain.shared.KeyValueCacheStorage
import com.usadapekora.bot.domain.shared.Logger
import com.usadapekora.bot.infraestructure.cache.RedisKeyValueCacheStorage
import com.usadapekora.bot.infraestructure.logger.Sfl4jLogger
import com.usadapekora.bot.application.trigger.find.TriggerFinder
import com.usadapekora.bot.application.trigger.read.TriggerAudioReader
import com.usadapekora.bot.application.trigger.update.TriggerUpdater
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerRepository
import com.usadapekora.bot.application.video.SendVideoFeed
import com.usadapekora.bot.application.video.VideoFeedParser
import com.usadapekora.bot.application.video.VideoFeedSubscriber
import com.usadapekora.bot.domain.shared.file.DomainFileDeleter
import com.usadapekora.bot.domain.shared.file.DomainFileReader
import com.usadapekora.bot.domain.shared.file.DomainFileWriter
import com.usadapekora.bot.domain.trigger.*
import com.usadapekora.bot.domain.video.ChannelSubscriber
import com.usadapekora.bot.domain.video.FeedParser
import com.usadapekora.bot.domain.video.VideoFeedNotifier
import com.usadapekora.bot.infraestructure.discord.DiscordTextChannelVideoNotifier
import com.usadapekora.bot.infraestructure.filesystem.FileSystemDomainFileDeleter
import com.usadapekora.bot.infraestructure.filesystem.FileSystemDomainFileReader
import com.usadapekora.bot.infraestructure.filesystem.FileSystemDomainFileWriter
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerAudioDefaultRepository
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerAudioRepository
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerTextRepository
import com.usadapekora.bot.infraestructure.youtube.YoutubeFeedSubscriber
import com.usadapekora.bot.infraestructure.youtube.YoutubeVideoParser
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single { RedisKeyValueCacheStorage() } bind KeyValueCacheStorage::class
    single { Sfl4jLogger() } bind Logger::class
    single { FileSystemDomainFileWriter() } bind DomainFileWriter::class
    single { FileSystemDomainFileDeleter() } bind DomainFileDeleter::class
    single { FileSystemDomainFileReader() } bind DomainFileReader::class
}

val triggerModule = module {
    single { MongoDbTriggerTextRepository() } bind TriggerTextResponseRepository::class
    single { MongoDbTriggerAudioRepository() } bind TriggerAudioResponseRepository::class
    single { MongoDbTriggerAudioDefaultRepository() } bind TriggerAudioDefaultRepository::class
    single { MongoDbTriggerRepository(get(), get()) } bind TriggerRepository::class
    single { TriggerMatcher() }
    single { TriggerFinder(get(), get()) }
    single { TriggerCreator(get(), get(), get()) }
    single { TriggerDeleter(get()) }
    single { TriggerUpdater(get()) }
    single { TriggerAudioCreator(get(), get()) }
    single { TriggerAudioFinder(get()) }
    single { TriggerAudioDeleter(get(), get()) }
    single { TriggerAudioReader(get(), get()) }
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
