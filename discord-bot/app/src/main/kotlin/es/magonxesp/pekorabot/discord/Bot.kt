package es.magonxesp.pekorabot.discord

import discord4j.core.DiscordClient
import es.magonxesp.pekorabot.discordBotToken
import es.magonxesp.pekorabot.modules.shared.domain.thread.ThreadRestartOnException
import kotlinx.coroutines.reactor.mono
import kotlin.concurrent.thread

val discordClient: DiscordClient = DiscordClient.create(discordBotToken)

fun startDiscordBot() {
    val thread = thread(start = true) {
        discordClient.withGateway {
            mono {
                it.handleEvents()
            }
        }.block()
    }

    thread.uncaughtExceptionHandler = ThreadRestartOnException()
}
