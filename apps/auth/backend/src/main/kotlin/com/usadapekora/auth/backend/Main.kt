package com.usadapekora.auth.backend

import com.usadapekora.auth.backend.routes.configureRoutes
import com.usadapekora.auth.modules
import com.usadapekora.shared.enableDependencyInjection
import com.usadapekora.shared.infrastructure.monitoring.MicrometerMonitoring.installMicrometer
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    enableDependencyInjection(modules = modules)

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

    installMicrometer()
    configureRoutes()
}
