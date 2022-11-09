package com.usadapekora.discord

import discord4j.core.DiscordClient
import com.usadapekora.context.discordBotToken
import com.usadapekora.context.shared.domain.thread.ExitOnThreadUncaughtException
import kotlinx.coroutines.reactor.mono
import kotlin.concurrent.thread

val discordClient: DiscordClient = DiscordClient.create(discordBotToken)

fun connectBot() {
    discordClient.withGateway {
        mono {
            it.handleEvents()
        }
    }.block()
}

fun startDiscordBot() {
    val thread = thread(start = true, block = ::connectBot)
    thread.uncaughtExceptionHandler = ExitOnThreadUncaughtException()
}
