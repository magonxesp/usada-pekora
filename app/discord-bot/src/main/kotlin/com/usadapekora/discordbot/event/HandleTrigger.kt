package com.usadapekora.discordbot.event

import discord4j.core.event.domain.message.MessageCreateEvent
import com.usadapekora.context.infraestructure.prometheus.registerTriggerFired
import com.usadapekora.context.application.trigger.find.TriggerFinder
import com.usadapekora.context.domain.trigger.TriggerException
import kotlinx.coroutines.reactor.awaitSingle
import org.koin.java.KoinJavaComponent.inject
import java.util.logging.Level
import java.util.logging.Logger


private val finder: TriggerFinder by inject(TriggerFinder::class.java)

suspend fun MessageCreateEvent.handleTrigger(): Boolean {
    val channel = message.channel.awaitSingle()

    try {
        val guildId = message.guildId.get().asString()
        val trigger = finder.findByInput(message.content, guildId)

        if (trigger.outputText != null) {
            channel.createMessage(trigger.outputText).awaitSingle()
        }

// TODO("Get output audio through mongodb")
//        if (trigger.outputSound != null) {
//            playAudio(trigger.outputSound!!)
//        }

        registerTriggerFired()

        return true
    } catch (exception: TriggerException.NotFound) {
        Logger.getLogger(MessageCreateEvent::class.toString()).log(Level.INFO, "Trigger not found for input ${message.content}", exception)
    }

    return false
}
