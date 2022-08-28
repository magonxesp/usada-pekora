package es.magonxesp.pekorabot.modules.video.infraestructure.youtube

import es.magonxesp.pekorabot.httpBaseUrl
import es.magonxesp.pekorabot.modules.video.domain.ChannelSubscriber
import es.magonxesp.pekorabot.modules.video.domain.VideoException
import es.magonxesp.pekorabot.youtubeChannelId
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class YoutubeFeedSubscriber : ChannelSubscriber {
    private val topicUrl = "https://www.youtube.com/xml/feeds/videos.xml?channel_id=$youtubeChannelId"
    private val callback = "$httpBaseUrl/webhook/youtube/feed"

    override suspend fun subscribe() {
        val client = HttpClient()
        val response = client.submitForm(
            url = "https://pubsubhubbub.appspot.com/subscribe",
            formParameters = Parameters.build {
                append("hub.mode", "subscribe")
                append("hub.callback", callback)
                append("hub.lease_seconds", (60 * 60 * 24 * 365).toString())
                append("hub.topic", topicUrl)
                append("hub.verify", "async")
            }
        )

        if (!response.status.isSuccess()) {
            throw VideoException.FeedSubscribe("""
                Youtube subscription error with 
                response status ${response.status} and body ${response.body<String>()}
                to topic url $topicUrl and callback url $callback
            """.trimIndent())
        }
    }
}
