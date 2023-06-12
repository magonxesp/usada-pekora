package com.usadapekora.bot

import com.usadapekora.bot.application.guild.GuildPreferenceCreator
import com.usadapekora.bot.application.guild.GuildPreferenceDeleter
import com.usadapekora.bot.application.guild.GuildPreferencesFinder
import com.usadapekora.bot.application.trigger.create.audio.TriggerDefaultAudioResponseCreator
import com.usadapekora.bot.application.trigger.create.TriggerCreator
import com.usadapekora.bot.application.trigger.create.text.TriggerTextResponseCreator
import com.usadapekora.bot.application.trigger.delete.audio.TriggerDefaultAudioDeleter
import com.usadapekora.bot.application.trigger.delete.TriggerDeleter
import com.usadapekora.bot.application.trigger.delete.text.TriggerTextResponseDeleter
import com.usadapekora.bot.application.trigger.find.audio.TriggerDefaultAudioFinder
import com.usadapekora.bot.domain.guild.GuildPreferencesRepository
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.MongoDbGuildPreferencesRepository
import com.usadapekora.shared.domain.KeyValueRepository
import com.usadapekora.shared.domain.Logger
import com.usadapekora.shared.infrastructure.persistence.redis.RedisKeyValueRepository
import com.usadapekora.shared.infrastructure.Sfl4jLogger
import com.usadapekora.bot.application.trigger.find.TriggerFinder
import com.usadapekora.bot.application.trigger.find.text.TriggerTextResponseFinder
import com.usadapekora.bot.application.trigger.read.TriggerDefaultAudioReader
import com.usadapekora.bot.application.trigger.update.TriggerUpdater
import com.usadapekora.bot.application.trigger.update.audio.TriggerDefaultAudioResponseUpdater
import com.usadapekora.bot.application.trigger.update.text.TriggerTextResponseUpdater
import com.usadapekora.bot.infraestructure.trigger.persistence.mongodb.MongoDbTriggerRepository
import com.usadapekora.bot.application.video.SendVideoFeed
import com.usadapekora.bot.application.video.VideoFeedParser
import com.usadapekora.bot.application.video.VideoFeedSubscriber
import com.usadapekora.shared.domain.file.DomainFileDeleter
import com.usadapekora.shared.domain.file.DomainFileReader
import com.usadapekora.shared.domain.file.DomainFileWriter
import com.usadapekora.bot.domain.trigger.*
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository
import com.usadapekora.bot.domain.video.ChannelSubscriber
import com.usadapekora.bot.domain.video.FeedParser
import com.usadapekora.bot.domain.video.VideoFeedNotifier
import com.usadapekora.bot.infraestructure.video.discord.DiscordTextChannelVideoNotifier
import com.usadapekora.shared.infrastructure.file.filesystem.FileSystemDomainFileDeleter
import com.usadapekora.shared.infrastructure.file.filesystem.FileSystemDomainFileReader
import com.usadapekora.shared.infrastructure.file.filesystem.FileSystemDomainFileWriter
import com.usadapekora.bot.infraestructure.trigger.persistence.mongodb.MongoDbTriggerAudioDefaultRepository
import com.usadapekora.bot.infraestructure.trigger.persistence.mongodb.MongoDbTriggerAudioRepository
import com.usadapekora.bot.infraestructure.trigger.persistence.mongodb.MongoDbTriggerTextRepository
import com.usadapekora.bot.infraestructure.video.youtube.YoutubeFeedSubscriber
import com.usadapekora.bot.infraestructure.video.youtube.YoutubeVideoParser
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single { RedisKeyValueRepository() } bind KeyValueRepository::class
    single { Sfl4jLogger() } bind Logger::class
    single { FileSystemDomainFileWriter() } bind DomainFileWriter::class
    single { FileSystemDomainFileDeleter() } bind DomainFileDeleter::class
    single { FileSystemDomainFileReader() } bind DomainFileReader::class
}

val triggerModule = module {
    single { MongoDbTriggerTextRepository() } bind TriggerTextResponseRepository::class
    single { MongoDbTriggerAudioRepository() } bind TriggerAudioResponseRepository::class
    single { MongoDbTriggerAudioDefaultRepository() } bind TriggerAudioDefaultRepository::class
    single { MongoDbTriggerRepository() } bind TriggerRepository::class
    single { TriggerMatcher() }
    single { TriggerFinder(get(), get()) }
    single { TriggerCreator(get(), get(), get()) }
    single { TriggerDeleter(get()) }
    single { TriggerUpdater(get(), get(), get()) }
    single { TriggerTextResponseCreator(get()) }
    single { TriggerTextResponseFinder(get()) }
    single { TriggerTextResponseDeleter(get()) }
    single { TriggerTextResponseUpdater(get()) }
    single { TriggerDefaultAudioResponseCreator(get(), get()) }
    single { TriggerDefaultAudioFinder(get()) }
    single { TriggerDefaultAudioDeleter(get(), get()) }
    single { TriggerDefaultAudioReader(get(), get()) }
    single { TriggerDefaultAudioResponseUpdater(get(), get(), get()) }
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

val modules = listOf(
    sharedModule,
    triggerModule,
    guildModule,
    videoModule
)
