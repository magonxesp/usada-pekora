package com.usadapekora.bot.discordbot

import com.usadapekora.bot.discordBotToken
import com.usadapekora.bot.modules
import com.usadapekora.shared.enableDependencyInjection
import discord4j.core.DiscordClient
import io.prometheus.client.exporter.HTTPServer
import io.prometheus.client.hotspot.DefaultExports
import kotlinx.coroutines.reactor.mono


fun main() {
    DefaultExports.initialize()
    enableDependencyInjection(modules = modules)
    HTTPServer.Builder().withPort(8081).build()

    DiscordClient.create(discordBotToken).withGateway {
        mono {
            it.handleEvents()
        }
    }.block()
}
