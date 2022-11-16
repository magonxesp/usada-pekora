package com.usadapekora.context.video.infraestructure.discord

import discord4j.common.util.Snowflake
import discord4j.discordjson.json.ImmutableMessageEditRequest
import com.usadapekora.context.shared.domain.KeyValueCacheStorage
import com.usadapekora.context.video.domain.Video
import com.usadapekora.context.video.domain.VideoFeedNotifier
import com.usadapekora.context.discordBotToken
import discord4j.core.DiscordClient
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking

class DiscordTextChannelVideoNotifier(private val cache: KeyValueCacheStorage) : VideoFeedNotifier {
    override fun notify(video: Video, target: String) {
        val lastVideoCacheKey = "feed_last_video_id_sent_$target"
        val lastVideoMessageCacheKey = "feed_last_video_${video.id}_message_$target"
        val lastVideoId = cache.get(lastVideoCacheKey)
        val channel = DiscordClient.create(discordBotToken).getChannelById(Snowflake.of(target))
        val content = "**${video.title}** ${video.url}"

        runBlocking {
            if (lastVideoId != video.id) {
                val message = channel.createMessage(content).awaitSingle()
                cache.set(lastVideoCacheKey, video.id)
                cache.set(lastVideoMessageCacheKey, message.id().toString())
            } else {
                val messageId = cache.get(lastVideoMessageCacheKey)
                val editRequest = ImmutableMessageEditRequest.builder().contentOrNull(content).build()
                channel.getRestMessage(Snowflake.of(messageId!!)).edit(editRequest).awaitSingle()
            }
        }
    }
}
