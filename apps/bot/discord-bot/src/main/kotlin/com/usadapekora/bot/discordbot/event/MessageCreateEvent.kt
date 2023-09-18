package com.usadapekora.bot.discordbot.event

import com.usadapekora.bot.discordbot.registerDiscordGuildCount
import com.usadapekora.bot.discordbot.registerProcessedMessage
import com.usadapekora.bot.discordbot.registerReceivedMessage
import com.usadapekora.shared.domain.LoggerFactory
import discord4j.core.event.domain.message.MessageCreateEvent
import kotlinx.coroutines.reactor.awaitSingle
import org.koin.java.KoinJavaComponent

private val loggerFactory: LoggerFactory by KoinJavaComponent.inject(LoggerFactory::class.java)
private val logger = loggerFactory.getLogger("com.pekorabot.discord.event.MessageCreateEvent")

suspend fun MessageCreateEvent.beforeHandleMessage() {
    val author = message.author.get()
    val guild = message.guild.awaitSingle()

    logger.info("Message received from discord by ${author.username} on guild ${guild.name} (${guild.id.asString()}); message id ${message.id.asString()}")
    registerReceivedMessage()
    registerDiscordGuildCount(client.guilds.count().awaitSingle()) // TODO: use new registry
}

fun MessageCreateEvent.afterHandleMessage() {
    logger.info("Handling message events for message id ${message.id.asString()}")
    registerProcessedMessage()
}

suspend fun MessageCreateEvent.handleEvents() {
    try {
        val author = message.author.get()

        beforeHandleMessage()

        if (author.isBot) {
            return
        }

        afterHandleMessage()

        if (handleCommand()) return
        if (handleTrigger()) return
    } catch (exception: Exception) {
        logger.warning("Failed handled events, exception ${exception::class} with message: ${exception.message}", exception)
    }
}
