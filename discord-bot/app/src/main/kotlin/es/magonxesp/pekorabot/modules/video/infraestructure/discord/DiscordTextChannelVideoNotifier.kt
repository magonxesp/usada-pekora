package es.magonxesp.pekorabot.modules.video.infraestructure.discord

import discord4j.common.util.Snowflake
import es.magonxesp.pekorabot.discord.discordClient
import es.magonxesp.pekorabot.modules.video.domain.Video
import es.magonxesp.pekorabot.modules.video.domain.VideoFeedNotifier
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking

class DiscordTextChannelVideoNotifier : VideoFeedNotifier {
    override fun notify(video: Video, target: String): Unit = runBlocking {
        val channel = discordClient.getChannelById(Snowflake.of(target))
        channel.createMessage("**${video.title}** ${video.url}").awaitSingle()
    }
}
