package com.usadapekora.bot.discordbot

import com.usadapekora.bot.discordbot.event.handleEvents
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import kotlinx.coroutines.reactive.asFlow

suspend fun GatewayDiscordClient.handleEvents() {
    on(MessageCreateEvent::class.java).asFlow().collect {
        it.handleEvents()
    }
}
