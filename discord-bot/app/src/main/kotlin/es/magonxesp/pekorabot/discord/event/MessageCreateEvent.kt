package es.magonxesp.pekorabot.discord.event

import discord4j.core.event.domain.message.MessageCreateEvent
import java.util.logging.Level
import java.util.logging.Logger

private val logger = Logger.getLogger("MessageCreateEvent")

suspend fun MessageCreateEvent.handleEvents() {
    try {
        val author = message.author.get()

        if (author.isBot) {
            return
        }

        if (handleCommand()) return
        if (handleTrigger()) return
    } catch (exception: Exception) {
        logger.log(Level.WARNING, "Failed handled events, exception ${exception::class} with message: ${exception.message}", exception)
    }
}
