package com.usadapekora.bot.backend

import com.usadapekora.bot.backend.routes.configureRoutes
import com.usadapekora.bot.modules
import com.usadapekora.shared.enableDependencyInjection
import com.usadapekora.shared.infrastructure.common.ktor.defaultConfiguration
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.prometheus.client.hotspot.DefaultExports
import kotlinx.coroutines.launch

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

    authentication {
        jwt {
            defaultConfiguration()
        }
    }

    configureRoutes()

    launch {
        scheduleJobs()
    }
}
