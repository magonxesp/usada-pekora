package com.usadapekora.http.controller.webhook

import com.usadapekora.context.guild.application.GuildPreferencesFinder
import com.usadapekora.context.guild.domain.GuildPreferences
import com.usadapekora.context.video.application.SendVideoFeed
import com.usadapekora.context.video.application.VideoFeedParser
import com.usadapekora.context.video.domain.VideoException
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

@Controller
@RequestMapping("/webhook/youtube")
class YoutubeFeedWebhookController {

    private val logger = Logger.getLogger(YoutubeFeedWebhookController::class.toString())
    private val finder: GuildPreferencesFinder by inject(GuildPreferencesFinder::class.java)
    private val sender: SendVideoFeed by inject(SendVideoFeed::class.java)
    private val parser: VideoFeedParser by inject(VideoFeedParser::class.java)

    @PostMapping("/feed")
    fun postFeed(@RequestBody body: String): ResponseEntity<String> {
        logger.info("incoming Youtube feed notification: $body")

        try {
            val subscribed = finder.findByPreference(GuildPreferences.GuildPreference.FeedChannelId)
            val video = parser.parse(body)

            sender.send(
                video = video,
                targets = subscribed.map {
                    it.preferences[GuildPreferences.GuildPreference.FeedChannelId] as String
                }.toTypedArray()
            )
        } catch (exception: Exception) {
            logger.log(Level.WARNING, exception.message, exception)

            return when (exception) {
                is VideoException.FeedParse -> ResponseEntity.badRequest().body("")
                else -> ResponseEntity.internalServerError().body("")
            }
        }

        return ResponseEntity.ok().body("")
    }

    @GetMapping("/feed", produces = ["text/plain; charset=UTF-8"])
    @ResponseBody
    fun hubChallenge(@RequestParam("hub.challenge") challenge: Optional<String>): String
        = if (challenge.isPresent) challenge.get() else ""

}