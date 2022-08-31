package es.magonxesp.pekorabot.modules.video.infraestructure.discord

import discord4j.common.util.Snowflake
import discord4j.discordjson.json.ImmutableMessageEditRequest
import es.magonxesp.pekorabot.discord.discordClient
import es.magonxesp.pekorabot.modules.shared.domain.KeyValueCacheStorage
import es.magonxesp.pekorabot.modules.video.domain.Video
import es.magonxesp.pekorabot.modules.video.domain.VideoFeedNotifier
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking

class DiscordTextChannelVideoNotifier(private val cache: KeyValueCacheStorage) : VideoFeedNotifier {
    override fun notify(video: Video, target: String) {
        val lastVideoCacheKey = "feed_last_video_id_sent"
        val lastVideoId = cache.get(lastVideoCacheKey)
        val channel = discordClient.getChannelById(Snowflake.of(target))
        val content = "**${video.title}** ${video.url}"

        runBlocking {
            if (lastVideoId != video.id) {
                val message = channel.createMessage(content).awaitSingle()
                cache.set(lastVideoCacheKey, video.id)
                cache.set("feed_last_video_${video.id}_message", message.id().toString())
            } else {
                val messageId = cache.get("feed_last_video_${video.id}_message")
                val editRequest = ImmutableMessageEditRequest.builder().contentOrNull(content).build()
                channel.getRestMessage(Snowflake.of(messageId!!)).edit(editRequest).awaitSingle()
            }
        }
    }
}
