package es.magonxesp.pekorabot.discord.event

import discord4j.core.event.domain.message.MessageCreateEvent
import es.magonxesp.pekorabot.modules.shared.infraestructure.prometheus.registerGuildCount
import es.magonxesp.pekorabot.modules.shared.infraestructure.prometheus.registerMessageRequest
import es.magonxesp.pekorabot.modules.shared.infraestructure.prometheus.registerProccesedMessageRequest
import kotlinx.coroutines.reactor.awaitSingle
import java.util.logging.Level
import java.util.logging.Logger

private val logger = Logger.getLogger("es.magonxesp.pekorabot.discord.event.MessageCreateEvent")

suspend fun MessageCreateEvent.beforeHandleMessage() {
    val author = message.author.get()
    val guild = message.guild.awaitSingle()

    logger.info("Message received from discord by ${author.username} on guild ${guild.name} (${guild.id.asString()}); message id ${message.id.asString()}")
    registerMessageRequest()
    registerGuildCount(client.guilds.count().awaitSingle())
}

fun MessageCreateEvent.afterHandleMessage() {
    logger.info("Handling message events for message id ${message.id.asString()}")
    registerProccesedMessageRequest()
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
        logger.log(Level.WARNING, "Failed handled events, exception ${exception::class} with message: ${exception.message}", exception)
    }
}
