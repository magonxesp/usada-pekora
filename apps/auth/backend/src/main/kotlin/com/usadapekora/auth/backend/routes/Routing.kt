package com.usadapekora.auth.backend.routes

import com.usadapekora.auth.backend.routes.api.oauth.authorizationProviderRoutesV1
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRoutes() {
    routing {
        wellKnown()
        oauthRoutes()
        authorizationProviderRoutesV1()
        prometheus()
    }
}
