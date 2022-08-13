package es.magonxesp.pekorabot.http.schedules

import es.magonxesp.pekorabot.httpBaseUrl
import es.magonxesp.pekorabot.youtubeChannelId
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.logging.Logger
import javax.annotation.PostConstruct

@Component
class YoutubeFeedSubscribeSchedule {

    private val topic = "https://www.youtube.com/xml/feeds/videos.xml?channel_id=$youtubeChannelId"
    private val callback = "$httpBaseUrl/webhook/youtube/feed"

    @PostConstruct
    fun startup() = subscribe()

    @Scheduled(cron = "0 0 * * * ?")
    fun subscribeSchedule() = subscribe()

    fun subscribe(): Unit = runBlocking {
        val response = HttpClient().post("https://pubsubhubbub.appspot.com/subscribe") {
            formData {
                append("hub.mode", "subscribe")
                append("hub.callback", callback)
                append("hub.lease_seconds", 60 * 60 * 24 * 365)
                append("hub.topic", topic)
                append("hub.verify", "async")
            }

            headers {
                contentType(ContentType.Application.FormUrlEncoded)
            }
        }

        Logger.getLogger(YoutubeFeedSubscribeSchedule::class.toString()).apply {
            info("Youtube pubsubhubbub subscription request has made for topic $topic with callback url $callback")
            info("Youtube subscription response status ${response.status} with body: ${response.body<String>()}")
        }
    }

}
