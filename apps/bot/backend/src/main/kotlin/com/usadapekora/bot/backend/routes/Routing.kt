package com.usadapekora.bot.backend.routes

import com.usadapekora.bot.backend.routes.api.trigger.triggerDefaultAudioV1
import com.usadapekora.bot.backend.routes.api.trigger.triggerTextV1
import com.usadapekora.bot.backend.routes.api.trigger.triggerV1
import com.usadapekora.bot.backend.routes.api.user.userV1
import com.usadapekora.bot.backend.routes.webhook.youtubeWebhook
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRoutes() {
    routing {
        youtubeWebhook()
        triggerV1()
        triggerDefaultAudioV1()
        triggerTextV1()
        userV1()
        prometheus()
    }
}
