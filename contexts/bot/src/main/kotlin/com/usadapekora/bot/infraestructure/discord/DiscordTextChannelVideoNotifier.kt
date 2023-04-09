package com.usadapekora.bot.infraestructure.discord

import discord4j.common.util.Snowflake
import discord4j.discordjson.json.ImmutableMessageEditRequest
import com.usadapekora.bot.domain.shared.KeyValueCacheStorage
import com.usadapekora.bot.domain.video.Video
import com.usadapekora.bot.domain.video.VideoFeedNotifier
import com.usadapekora.bot.discordBotToken
import com.usadapekora.bot.infraestructure.prometheus.registerVideoNotification
import discord4j.core.DiscordClient
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import java.util.logging.Logger

class DiscordTextChannelVideoNotifier(private val cache: KeyValueCacheStorage) : VideoFeedNotifier {

    private val logger = Logger.getLogger(DiscordTextChannelVideoNotifier::class.toString())

    override suspend fun notify(video: Video, target: String) {
        logger.info("Starting sending youtube notification for video id ${video.id} to channel id $target")

        val lastVideoCacheKey = "feed_last_video_id_sent_$target"
        val lastVideoMessageCacheKey = "feed_last_video_${video.id}_message_$target"
        val lastVideoId = cache.get(lastVideoCacheKey)
        val channel = DiscordClient.create(discordBotToken).getChannelById(Snowflake.of(target))
        val content = "**${video.title}** ${video.url}"

        try {
            withTimeout(10000) {
                if (lastVideoId != video.id) {
                    channel.createMessage(content).awaitSingle().apply {
                        cache.set(lastVideoCacheKey, video.id)
                        cache.set(lastVideoMessageCacheKey, id().toString())
                        logger.info("Notification message created on channel id $target")
                    }
                } else {
                    val messageId = cache.get(lastVideoMessageCacheKey)
                    ImmutableMessageEditRequest.builder().contentOrNull(content).build().apply {
                        channel.getRestMessage(Snowflake.of(messageId!!)).edit(this).awaitSingle()
                        logger.info("Notification message with id $messageId edited on channel id $target")
                    }
                }

                logger.info("Notification for video id ${video.id} sent to channel id $target")
                registerVideoNotification()
            }
        } catch (_: TimeoutCancellationException) {
            logger.warning("The notification send was exceed the timeout time of 10 second")
        }
    }
}
