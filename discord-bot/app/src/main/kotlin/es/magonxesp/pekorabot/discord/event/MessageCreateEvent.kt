package es.magonxesp.pekorabot.discord.event

import discord4j.core.event.domain.message.MessageCreateEvent
import es.magonxesp.pekorabot.discord.voice.joinVoiceChannel
import es.magonxesp.pekorabot.discord.voice.playAudio
import es.magonxesp.pekorabot.modules.trigger.domain.TriggerException
import es.magonxesp.pekorabot.triggerFinder
import kotlinx.coroutines.reactor.awaitSingle
import java.util.logging.Logger

suspend fun MessageCreateEvent.handleTrigger() {
    val author = message.author.get()

    if (author.isBot) {
        return
    }

    val channel = message.channel.awaitSingle()

    try {
        val guildId = message.guildId.get().asString()
        val trigger = triggerFinder().findByInput(message.content, guildId)

        if (trigger.outputText != null) {
            channel.createMessage(trigger.outputText).awaitSingle()
        }

        if (trigger.outputSound != null) {
            playAudio(trigger.outputSound)
        }
    } catch (exception: TriggerException.NotFound) {
        Logger.getLogger(MessageCreateEvent::class.toString()).warning("Trigger not found for input ${message.content}")
    }
}

suspend fun MessageCreateEvent.handleCommand() {

}

suspend fun MessageCreateEvent.handleEvents() {
    handleTrigger()
    /*handleCommand()

    return Mono.empty<Void>()*/
}
