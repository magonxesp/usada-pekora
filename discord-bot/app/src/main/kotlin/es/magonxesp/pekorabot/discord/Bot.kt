package es.magonxesp.pekorabot.discord

import discord4j.core.DiscordClient
import es.magonxesp.pekorabot.discordBotToken
import kotlinx.coroutines.reactor.mono

val discordClient: DiscordClient = DiscordClient.create(discordBotToken)

fun startDiscordBot() {
    discordClient.withGateway {
        mono {
            it.handleEvents()
        }
    }.block()
}
