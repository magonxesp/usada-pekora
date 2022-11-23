package com.usadapekora.discordbot

import discord4j.core.DiscordClient
import com.usadapekora.context.discordBotToken
import kotlinx.coroutines.reactor.mono


fun main() {
    DiscordClient.create(discordBotToken).withGateway {
        mono {
            it.handleEvents()
        }
    }.block()
}
