package es.magonxesp.pekorabot.discord.event

import discord4j.core.event.domain.message.MessageCreateEvent
import es.magonxesp.pekorabot.discord.voice.playAudio
import es.magonxesp.pekorabot.modules.shared.infraestructure.prometheus.registerTriggerFired
import es.magonxesp.pekorabot.modules.trigger.application.TriggerFinder
import es.magonxesp.pekorabot.modules.trigger.domain.TriggerException
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

        if (trigger.outputSound != null) {
            playAudio(trigger.outputSound)
        }

        registerTriggerFired()

        return true
    } catch (exception: TriggerException.NotFound) {
        Logger.getLogger(MessageCreateEvent::class.toString()).log(Level.INFO, "Trigger not found for input ${message.content}", exception)
    }

    return false
}
