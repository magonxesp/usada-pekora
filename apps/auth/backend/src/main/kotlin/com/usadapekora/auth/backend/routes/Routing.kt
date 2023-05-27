package com.usadapekora.auth.backend.routes

import com.usadapekora.auth.backend.routes.api.apiRoutesV1
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRoutes() {
    routing {
        oauthRoutes()
        apiRoutesV1()
    }
}
