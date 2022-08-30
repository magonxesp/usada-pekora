package es.magonxesp.pekorabot.discord.shared

import discord4j.core.`object`.entity.Message

abstract class CommandHandler {
    open val commandArguments: Array<CommandArgument<*>> = arrayOf()
    abstract suspend fun handle(message: Message, args: Map<String, Any?>)
}
