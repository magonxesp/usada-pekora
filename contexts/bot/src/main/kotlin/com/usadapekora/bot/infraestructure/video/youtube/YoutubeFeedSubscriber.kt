package com.usadapekora.bot.infraestructure.video.youtube

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.video.ChannelSubscriber
import com.usadapekora.bot.backendBaseUrl
import com.usadapekora.bot.domain.video.VideoException
import com.usadapekora.bot.youtubeChannelId
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class YoutubeFeedSubscriber : ChannelSubscriber {
    private val topicUrl = "https://www.youtube.com/xml/feeds/videos.xml?channel_id=$youtubeChannelId"
    private val callback = "$backendBaseUrl/webhook/youtube/feed"

    override suspend fun subscribe(): Either<VideoException.FeedSubscribe, Unit> {
        val client = HttpClient()
        val response = client.submitForm(
            url = "https://pubsubhubbub.appspot.com/subscribe",
            formParameters = Parameters.build {
                append("hub.mode", "subscribe")
                append("hub.oauth", callback)
                append("hub.lease_seconds", (60 * 60 * 24 * 365).toString())
                append("hub.topic", topicUrl)
                append("hub.verify", "async")
            }
        )

        if (!response.status.isSuccess()) {
            return VideoException.FeedSubscribe("""
                Youtube subscription error with 
                response status ${response.status} and body ${response.body<String>()}
                to topic url $topicUrl and oauth url $callback
            """.trimIndent()).left()
        }

        return Unit.right()
    }
}
