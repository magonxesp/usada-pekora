package es.magonxesp.pekorabot.discord.event

import discord4j.core.event.domain.message.MessageCreateEvent
import kotlinx.coroutines.reactor.awaitSingle
import java.util.logging.Level
import java.util.logging.Logger

private val logger = Logger.getLogger("MessageCreateEvent")

suspend fun MessageCreateEvent.handleEvents() {
    try {
        val author = message.author.get()
        val guild = message.guild.awaitSingle()

        logger.info("Message received from discord by ${author.username} on guild ${guild.name} (${guild.id.asString()}); message id ${message.id.asString()}")

        if (author.isBot) {
            return
        }

        logger.info("Handling message events for message id ${message.id.asString()}")

        if (handleCommand()) return
        if (handleTrigger()) return
    } catch (exception: Exception) {
        logger.log(Level.WARNING, "Failed handled events, exception ${exception::class} with message: ${exception.message}", exception)
    }
}
