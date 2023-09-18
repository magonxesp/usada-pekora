package com.usadapekora.bot.backend.routes

import com.usadapekora.shared.infrastructure.monitoring.MicrometerMonitoring
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.prometheus() {
    get("/metrics") {
        call.respond(MicrometerMonitoring.micrometerRegistry.scrape())
    }
}
