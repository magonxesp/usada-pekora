package es.magonxesp.pekorabot.discord.event

import discord4j.core.event.domain.message.MessageCreateEvent
import es.magonxesp.pekorabot.discord.defaultCommandPrefix
import es.magonxesp.pekorabot.discord.voice.playAudio
import es.magonxesp.pekorabot.modules.trigger.domain.TriggerException
import es.magonxesp.pekorabot.triggerFinder
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.util.ReflectionUtils
import java.util.logging.Logger
import kotlin.reflect.full.declaredFunctions

suspend fun MessageCreateEvent.handleTrigger() {
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
    if (!message.content.startsWith(defaultCommandPrefix)) {
        return
    }

    // TODO(Get all functions annotated by Command annotation)
    // https://stackoverflow.com/questions/31060995/how-do-i-get-the-declared-functions-of-a-kotlin-class-kclass-in-m12
    // https://kotlinlang.org/docs/reflection.html#bound-constructor-references
    // https://stackoverflow.com/questions/33907095/kotlin-how-can-i-use-reflection-on-packages
}

suspend fun MessageCreateEvent.handleEvents() {
    val author = message.author.get()

    if (author.isBot) {
        return
    }

    handleTrigger()
    handleCommand()
}
