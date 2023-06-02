package com.usadapekora.bot.backend.routes

import com.usadapekora.bot.backend.routes.api.trigger.triggerDefaultAudioV1
import com.usadapekora.bot.backend.routes.api.trigger.triggerV1
import com.usadapekora.bot.backend.routes.webhook.youtubeWebhook
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRoutes() {
    routing {
        youtubeWebhook()
        triggerV1()
        triggerDefaultAudioV1()
    }
}
