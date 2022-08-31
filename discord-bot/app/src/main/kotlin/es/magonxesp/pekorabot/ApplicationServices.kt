package es.magonxesp.pekorabot

import es.magonxesp.pekorabot.modules.guild.application.GuildPreferenceCreator
import es.magonxesp.pekorabot.modules.guild.application.GuildPreferenceDeleter
import es.magonxesp.pekorabot.modules.guild.application.GuildPreferencesFinder
import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferencesRepository
import es.magonxesp.pekorabot.modules.guild.infraestructure.persistence.MongoDbGuildPreferencesRepository
import es.magonxesp.pekorabot.modules.trigger.application.TriggerFinder
import es.magonxesp.pekorabot.modules.trigger.domain.TriggerMatcher
import es.magonxesp.pekorabot.modules.trigger.domain.TriggerRepository
import es.magonxesp.pekorabot.modules.trigger.infraestructure.persistence.StrapiTriggerRepository
import es.magonxesp.pekorabot.modules.video.application.SendVideoFeed
import es.magonxesp.pekorabot.modules.video.application.VideoFeedParser
import es.magonxesp.pekorabot.modules.video.application.VideoFeedSubscriber
import es.magonxesp.pekorabot.modules.video.domain.ChannelSubscriber
import es.magonxesp.pekorabot.modules.video.domain.FeedParser
import es.magonxesp.pekorabot.modules.video.domain.VideoFeedNotifier
import es.magonxesp.pekorabot.modules.video.infraestructure.discord.DiscordTextChannelVideoNotifier
import es.magonxesp.pekorabot.modules.video.infraestructure.youtube.YoutubeFeedSubscriber
import es.magonxesp.pekorabot.modules.video.infraestructure.youtube.YoutubeVideoParser
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module


private val triggerModule = module {
    factory { StrapiTriggerRepository() } bind TriggerRepository::class
    factory { TriggerMatcher() }
    factory { TriggerFinder(get(), get()) }
}

private val guildModule = module {
    factory { MongoDbGuildPreferencesRepository() } bind GuildPreferencesRepository::class
    factory { GuildPreferenceCreator(get()) }
    factory { GuildPreferenceDeleter(get()) }
    factory { GuildPreferencesFinder(get()) }
}

private val videoModule = module {
    factory { DiscordTextChannelVideoNotifier() } bind VideoFeedNotifier::class
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
                triggerModule,
                guildModule,
                videoModule
            ).plus(extraModules)
        )
    }
}
