package es.magonxesp.pekorabot.discord

import discord4j.core.DiscordClient
import kotlinx.coroutines.reactor.mono

fun startDiscordBot() {
    DiscordClient.create(System.getenv("DISCORD_BOT_TOKEN")).withGateway {
        mono {
            it.handleEvents()
        }
    }.block()
}
