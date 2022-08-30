package es.magonxesp.pekorabot

import es.magonxesp.pekorabot.modules.guild.application.GuildPreferenceCreator
import es.magonxesp.pekorabot.modules.guild.application.GuildPreferenceDeleter
import es.magonxesp.pekorabot.modules.guild.application.GuildPreferencesFinder
import es.magonxesp.pekorabot.modules.guild.infraestructure.persistence.MongoDbGuildPreferencesRepository
import es.magonxesp.pekorabot.modules.trigger.application.TriggerFinder
import es.magonxesp.pekorabot.modules.trigger.domain.TriggerMatcher
import es.magonxesp.pekorabot.modules.trigger.infraestructure.persistence.StrapiTriggerRepository
import es.magonxesp.pekorabot.modules.video.application.SendVideoFeed
import es.magonxesp.pekorabot.modules.video.application.VideoFeedSubscriber
import es.magonxesp.pekorabot.modules.video.infraestructure.discord.DiscordTextChannelVideoNotifier
import es.magonxesp.pekorabot.modules.video.infraestructure.youtube.YoutubeFeedSubscriber

private val triggerRepository = StrapiTriggerRepository()
private val guildPreferencesRepository = MongoDbGuildPreferencesRepository()

fun triggerFinder() = TriggerFinder(triggerRepository, TriggerMatcher())
fun videoFeedSubscriber() = VideoFeedSubscriber(YoutubeFeedSubscriber())
fun guildPreferenceCreator() = GuildPreferenceCreator(guildPreferencesRepository)
fun guildPreferenceDeleter() = GuildPreferenceDeleter(guildPreferencesRepository)
fun guildPreferenceFinder() = GuildPreferencesFinder(guildPreferencesRepository)
fun sendVideoFeed() = SendVideoFeed(DiscordTextChannelVideoNotifier())
