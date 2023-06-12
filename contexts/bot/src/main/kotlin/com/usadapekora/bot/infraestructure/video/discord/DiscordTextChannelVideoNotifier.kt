package com.usadapekora.bot.infraestructure.video.discord

import discord4j.common.util.Snowflake
import discord4j.discordjson.json.ImmutableMessageEditRequest
import com.usadapekora.shared.domain.KeyValueRepository
import com.usadapekora.bot.domain.video.Video
import com.usadapekora.bot.domain.video.VideoFeedNotifier
import com.usadapekora.bot.discordBotToken
import com.usadapekora.bot.infraestructure.trigger.prometheus.registerVideoNotification
import discord4j.core.DiscordClient
import kotlinx.coroutines.reactor.awaitSingle
import java.util.logging.Logger

class DiscordTextChannelVideoNotifier(private val cache: KeyValueRepository) : VideoFeedNotifier {

    private val logger = Logger.getLogger(DiscordTextChannelVideoNotifier::class.toString())

    override suspend fun notify(video: Video, target: String) {
        logger.info("Starting sending youtube notification for video id ${video.id} to channel id $target")

        val lastVideoCacheKey = "feed_last_video_id_sent_$target"
        val lastVideoMessageCacheKey = "feed_last_video_${video.id}_message_$target"
        val lastVideoId = cache.get(lastVideoCacheKey)
        val channel = DiscordClient.create(discordBotToken).getChannelById(Snowflake.of(target))
        val messageContent = "**${video.title}** ${video.url}"

        if (lastVideoId != video.id) {
            val message = channel.createMessage(messageContent).awaitSingle()
            cache.set(lastVideoCacheKey, video.id)
            cache.set(lastVideoMessageCacheKey, message.id().toString())
            logger.info("Notification message created on channel id $target")
        } else {
            val messageId = cache.get(lastVideoMessageCacheKey) ?: return
            val request = ImmutableMessageEditRequest.builder().contentOrNull(messageContent).build()
            channel.getRestMessage(Snowflake.of(messageId)).edit(request).awaitSingle()
            logger.info("Notification message with id $messageId edited on channel id $target")
        }

        registerVideoNotification()
    }
}
