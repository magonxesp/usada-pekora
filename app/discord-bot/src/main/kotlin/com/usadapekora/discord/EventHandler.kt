package com.usadapekora.discord

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import com.usadapekora.discord.event.handleEvents
import kotlinx.coroutines.reactive.asFlow

suspend fun GatewayDiscordClient.handleEvents() {
    on(MessageCreateEvent::class.java).asFlow().collect {
        it.handleEvents()
    }
}
