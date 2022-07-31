package es.magonxesp.pekorabot.discord.event

import discord4j.core.event.domain.message.MessageCreateEvent
import kotlinx.coroutines.reactor.awaitSingle

suspend fun MessageCreateEvent.handleTrigger() {
    val author = message.author.get()

    if (author.isBot) {
        return
    }

    val channel = message.channel.awaitSingle()
    channel.createMessage(message.content).awaitSingle()
}

suspend fun MessageCreateEvent.handleCommand() {

}

suspend fun MessageCreateEvent.handleEvents() {
    handleTrigger()
    /*handleCommand()

    return Mono.empty<Void>()*/
}
