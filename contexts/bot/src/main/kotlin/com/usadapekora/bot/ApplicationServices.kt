package com.usadapekora.bot

import com.usadapekora.bot.application.guild.create.CreateGuildsFromProviderOnAuthorizationGranted
import com.usadapekora.bot.application.guild.create.GuildCreator
import com.usadapekora.bot.application.guild.create.GuildMemberCreator
import com.usadapekora.bot.application.guild.create.GuildPreferenceCreator
import com.usadapekora.bot.application.guild.delete.GuildPreferenceDeleter
import com.usadapekora.bot.application.guild.find.GuildFinder
import com.usadapekora.bot.application.guild.find.GuildPreferencesFinder
import com.usadapekora.bot.application.guild.update.ProvidedGuildUpdater
import com.usadapekora.bot.application.trigger.create.CreateTriggerOnGuildCreate
import com.usadapekora.bot.application.trigger.create.TriggerCreator
import com.usadapekora.bot.application.trigger.create.audio.TriggerAudioResponseCreator
import com.usadapekora.bot.application.trigger.create.text.TriggerTextResponseCreator
import com.usadapekora.bot.application.trigger.delete.TriggerDeleter
import com.usadapekora.bot.application.trigger.delete.audio.TriggerAudioResponseDeleter
import com.usadapekora.bot.application.trigger.delete.text.TriggerTextResponseDeleter
import com.usadapekora.bot.application.trigger.find.TriggerFinder
import com.usadapekora.bot.application.trigger.find.audio.TriggerAudioResponseFinder
import com.usadapekora.bot.application.trigger.find.text.TriggerTextResponseFinder
import com.usadapekora.bot.application.trigger.read.TriggerAudioResponseReader
import com.usadapekora.bot.application.trigger.update.TriggerUpdater
import com.usadapekora.bot.application.trigger.update.audio.TriggerAudioResponseUpdater
import com.usadapekora.bot.application.trigger.update.text.TriggerTextResponseUpdater
import com.usadapekora.bot.application.video.SendVideoFeed
import com.usadapekora.bot.application.video.VideoFeedParser
import com.usadapekora.bot.application.video.VideoFeedSubscriber
import com.usadapekora.bot.domain.guild.*
import com.usadapekora.bot.domain.trigger.BuiltInTriggerRepository
import com.usadapekora.bot.domain.trigger.TriggerMatcher
import com.usadapekora.bot.domain.trigger.TriggerMonitoring
import com.usadapekora.bot.domain.trigger.TriggerRepository
import com.usadapekora.bot.domain.trigger.audio.BuiltInTriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.text.BuiltInTriggerTextResponseRepository
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository
import com.usadapekora.bot.domain.video.ChannelSubscriber
import com.usadapekora.bot.domain.video.FeedParser
import com.usadapekora.bot.domain.video.VideoFeedNotifier
import com.usadapekora.bot.domain.video.VideoMonitoring
import com.usadapekora.bot.infraestructure.guild.koin.KoinGuildProviderRepositoryFactory
import com.usadapekora.bot.infraestructure.guild.persistence.discord.DiscordGuildProviderRepository
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.MongoDbGuildMemberRepository
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.MongoDbGuildPreferencesRepository
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.MongoDbGuildRepository
import com.usadapekora.bot.infraestructure.trigger.monitoring.MicrometerTriggerMonitoring
import com.usadapekora.bot.infraestructure.trigger.persistence.json.JsonResourceBuiltInTriggerAudioRepository
import com.usadapekora.bot.infraestructure.trigger.persistence.json.JsonResourceBuiltInTriggerRepository
import com.usadapekora.bot.infraestructure.trigger.persistence.json.JsonResourceBuiltInTriggerTextRepository
import com.usadapekora.bot.infraestructure.trigger.persistence.mongodb.MongoDbTriggerAudioResponseRepository
import com.usadapekora.bot.infraestructure.trigger.persistence.mongodb.MongoDbTriggerRepository
import com.usadapekora.bot.infraestructure.trigger.persistence.mongodb.MongoDbTriggerTextRepository
import com.usadapekora.bot.infraestructure.video.discord.DiscordTextChannelVideoNotifier
import com.usadapekora.bot.infraestructure.video.monitoring.MicrometerVideoMonitoring
import com.usadapekora.bot.infraestructure.video.youtube.YoutubeFeedSubscriber
import com.usadapekora.bot.infraestructure.video.youtube.YoutubeVideoParser
import com.usadapekora.shared.domain.KeyValueRepository
import com.usadapekora.shared.domain.file.DomainFileDeleter
import com.usadapekora.shared.domain.file.DomainFileReader
import com.usadapekora.shared.domain.file.DomainFileWriter
import com.usadapekora.shared.infrastructure.file.filesystem.FileSystemDomainFileDeleter
import com.usadapekora.shared.infrastructure.file.filesystem.FileSystemDomainFileReader
import com.usadapekora.shared.infrastructure.file.filesystem.FileSystemDomainFileWriter
import com.usadapekora.shared.infrastructure.persistence.redis.RedisKeyValueRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single { RedisKeyValueRepository() } bind KeyValueRepository::class
    single { FileSystemDomainFileWriter() } bind DomainFileWriter::class
    single { FileSystemDomainFileDeleter() } bind DomainFileDeleter::class
    single { FileSystemDomainFileReader() } bind DomainFileReader::class
}

val triggerModule = module {
    single { MongoDbTriggerTextRepository() } bind TriggerTextResponseRepository::class
    single { MongoDbTriggerAudioResponseRepository() } bind TriggerAudioResponseRepository::class
    single { MongoDbTriggerRepository() } bind TriggerRepository::class
    single { JsonResourceBuiltInTriggerRepository() } bind BuiltInTriggerRepository::class
    single { JsonResourceBuiltInTriggerTextRepository() } bind BuiltInTriggerTextResponseRepository::class
    single { JsonResourceBuiltInTriggerAudioRepository() } bind BuiltInTriggerAudioResponseRepository::class
    single { MicrometerTriggerMonitoring() } bind TriggerMonitoring::class
    single { CreateTriggerOnGuildCreate(get(), get(), get(), get(), get(), get()) }
    single { TriggerMatcher() }
    single { TriggerFinder(get(), get(), get()) }
    single { TriggerCreator(get(), get(), get()) }
    single { TriggerDeleter(get()) }
    single { TriggerUpdater(get(), get(), get()) }
    single { TriggerTextResponseCreator(get()) }
    single { TriggerTextResponseFinder(get()) }
    single { TriggerTextResponseDeleter(get()) }
    single { TriggerTextResponseUpdater(get()) }
    single { TriggerAudioResponseCreator(get(), get()) }
    single { TriggerAudioResponseFinder(get()) }
    single { TriggerAudioResponseDeleter(get(), get()) }
    single { TriggerAudioResponseReader(get(), get()) }
    single { TriggerAudioResponseUpdater(get(), get(), get()) }
}

val guildModule = module {
    single { MongoDbGuildRepository() } bind GuildRepository::class
    single { MongoDbGuildMemberRepository() } bind GuildMemberRepository::class
    single { MongoDbGuildPreferencesRepository() } bind GuildPreferencesRepository::class
    single { KoinGuildProviderRepositoryFactory() } bind GuildProviderRepositoryFactory::class
    factory { (token: String) -> DiscordGuildProviderRepository(token) } bind GuildProviderRepository::class
    single { GuildCreator(get(), get(), get()) }
    single { GuildMemberCreator(get()) }
    single { GuildPreferenceCreator(get()) }
    single { GuildPreferenceDeleter(get()) }
    single { GuildPreferencesFinder(get()) }
    single { ProvidedGuildUpdater(get()) }
    single { CreateGuildsFromProviderOnAuthorizationGranted(get(), get(), get(), get(), get()) }
    single { GuildFinder(get()) }
}

val videoModule = module {
    single { DiscordTextChannelVideoNotifier(get()) } bind VideoFeedNotifier::class
    single { YoutubeVideoParser() } bind FeedParser::class
    single { YoutubeFeedSubscriber() } bind ChannelSubscriber::class
    single { MicrometerVideoMonitoring() } bind VideoMonitoring::class
    single { VideoFeedParser(get()) }
    single { VideoFeedSubscriber(get()) }
    single { SendVideoFeed(get(), get()) }
}

val modules = listOf(
    sharedModule,
    triggerModule,
    guildModule,
    videoModule
)
