package com.usadapekora.auth.backend.routes

import com.usadapekora.auth.backend.appMicrometerRegistry
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.prometheus() {
    get("/metrics") {
        call.respond(appMicrometerRegistry.scrape())
    }
}
