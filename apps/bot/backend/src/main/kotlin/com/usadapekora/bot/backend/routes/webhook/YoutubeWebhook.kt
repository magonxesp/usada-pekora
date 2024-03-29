package com.usadapekora.bot.backend.routes.webhook

import com.usadapekora.bot.application.guild.find.GuildPreferencesFinder
import com.usadapekora.bot.application.video.SendVideoFeed
import com.usadapekora.bot.application.video.VideoFeedParser
import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.bot.domain.video.VideoException
import com.usadapekora.shared.domain.LoggerFactory
import com.usadapekora.shared.infrastructure.ktor.respondError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val finder: GuildPreferencesFinder by inject(GuildPreferencesFinder::class.java)
private val sender: SendVideoFeed by inject(SendVideoFeed::class.java)
private val parser: VideoFeedParser by inject(VideoFeedParser::class.java)
private val loggerFactory: LoggerFactory by inject(LoggerFactory::class.java)
private val logger = loggerFactory.getLogger("com.usadapekora.bot.backend.routes.webhook.YoutubeWebhookKt")

fun errorStatusCode(error: VideoException) = when (error) {
    is VideoException.FeedParse -> HttpStatusCode.BadRequest
    else -> HttpStatusCode.InternalServerError
}

fun Route.youtubeWebhook() {
    route("/webhook/youtube/feed") {
        get {
            call.respond(call.request.queryParameters["hub.challenge"] ?: "")
        }
        post {
            val feedXml = call.receiveText()
            logger.info("incoming Youtube feed notification: $feedXml")

            val subscribed = finder.findByPreference(GuildPreferences.GuildPreference.FeedChannelId)
            val video = parser.parse(feedXml)
                .onLeft { return@post call.respondError(errorStatusCode(it), it.message ?: "") }
                .getOrNull()!!

            sender.send(
                video = video,
                targets = subscribed.map {
                    it.preferences[GuildPreferences.GuildPreference.FeedChannelId] as String
                }.toTypedArray()
            )

            call.respond(HttpStatusCode.OK)
        }
    }
}
