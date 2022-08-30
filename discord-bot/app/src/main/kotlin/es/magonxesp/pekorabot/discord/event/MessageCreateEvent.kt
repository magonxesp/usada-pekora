package es.magonxesp.pekorabot.discord.event

import discord4j.core.event.domain.message.MessageCreateEvent

suspend fun MessageCreateEvent.handleEvents() {
    val author = message.author.get()

    if (author.isBot) {
        return
    }

    if (handleCommand()) return
    if (handleTrigger()) return
}
