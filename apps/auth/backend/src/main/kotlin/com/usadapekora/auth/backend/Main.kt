package com.usadapekora.auth.backend

import com.usadapekora.auth.backend.routes.configureRoutes
import com.usadapekora.auth.modules
import com.usadapekora.shared.enableDependencyInjection
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.prometheus.client.hotspot.DefaultExports

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    enableDependencyInjection(modules = modules)
    DefaultExports.initialize()

    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        anyHost()
    }

    configureRoutes()
}
