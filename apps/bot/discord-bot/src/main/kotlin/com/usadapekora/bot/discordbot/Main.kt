package com.usadapekora.bot.discordbot

import com.usadapekora.bot.modules
import com.usadapekora.shared.discordBotToken
import com.usadapekora.shared.enableDependencyInjection
import com.usadapekora.shared.infrastructure.monitoring.MicrometerMonitoring
import com.usadapekora.shared.infrastructure.monitoring.MicrometerMonitoring.installMicrometer
import discord4j.core.DiscordClient
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.reactor.mono


fun main() {
    enableDependencyInjection(modules = modules)

    val server = embeddedServer(Netty, port = 8082) {
        installMicrometer()

        routing {
            get("/metrics") {
                call.respond(MicrometerMonitoring.micrometerRegistry.scrape())
            }
        }
    }

    server.start()

    DiscordClient.create(discordBotToken).withGateway {
        mono {
            it.handleEvents()
        }
    }.block()
}
