package es.magonxesp.pekorabot.discord

import discord4j.core.DiscordClient
import es.magonxesp.pekorabot.discordBotToken
import es.magonxesp.pekorabot.modules.shared.domain.thread.ExitOnThreadUncaughtException
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.reactor.mono
import java.util.concurrent.Executors
import kotlin.concurrent.thread

val discordClient: DiscordClient = DiscordClient.create(discordBotToken)

fun connectBot() {
    discordClient.withGateway {
        val dispatcher = Executors.newFixedThreadPool(4)
            .asCoroutineDispatcher()

        mono(dispatcher) {
            it.handleEvents()
        }
    }.block()
}

fun startDiscordBot() {
    val thread = thread(start = true, block = ::connectBot)
    thread.uncaughtExceptionHandler = ExitOnThreadUncaughtException()
}
