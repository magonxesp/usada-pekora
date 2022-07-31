package es.magonxesp.pekorabot.modules.video.infraestructure.youtube

import es.magonxesp.pekorabot.modules.video.domain.ChannelSubscriber
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.util.logging.Logger

class YoutubeFeedSubscriber(
    private val channelId: String
) : ChannelSubscriber {
    private val log = Logger.getLogger(YoutubeFeedSubscriber::class.java.name)
    private val topicUrl: String = "https://www.youtube.com/xml/feeds/videos.xml?channel_id=$channelId"

    override suspend fun subscribe() {
        val client = HttpClient()

        val response = client.post("https://pubsubhubbub.appspot.com/subscribe") {
            setBody(mapOf(
                "hub.mode" to "subscribe",
                "hub.callback" to "/webhook/youtube/feed",
                "hub.lease_seconds" to (60 * 60 * 24 * 365),
                "hub.topic" to topicUrl,
                "hub.verify" to "async"
            ))

            contentType(ContentType.Application.FormUrlEncoded)
        }

        log.info("[youtube feed subscribe response] ${response.status}: ${response.body<String>()}")
    }
}
