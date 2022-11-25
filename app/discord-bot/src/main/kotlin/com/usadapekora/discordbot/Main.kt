package com.usadapekora.discordbot

import discord4j.core.DiscordClient
import com.usadapekora.context.discordBotToken
import com.usadapekora.context.enableDependencyInjection
import io.prometheus.client.hotspot.DefaultExports
import kotlinx.coroutines.reactor.mono
import io.prometheus.client.exporter.HTTPServer


fun main() {
    DefaultExports.initialize()
    enableDependencyInjection()
    HTTPServer.Builder().withPort(8081).build()

    DiscordClient.create(discordBotToken).withGateway {
        mono {
            it.handleEvents()
        }
    }.block()
}
