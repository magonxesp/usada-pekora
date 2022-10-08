package es.magonxesp.pekorabot.discord.event

import discord4j.core.event.domain.message.MessageCreateEvent
import es.magonxesp.pekorabot.discord.shared.annotation.Command
import es.magonxesp.pekorabot.discord.commands
import es.magonxesp.pekorabot.discord.defaultCommandPrefix
import es.magonxesp.pekorabot.discord.shared.CommandHandler
import es.magonxesp.pekorabot.discord.shared.exception.CommandException
import es.magonxesp.pekorabot.modules.shared.infraestructure.prometheus.registerCommandFired
import kotlinx.coroutines.reactor.awaitSingle
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.reflect.KClass
import kotlin.reflect.full.cast
import kotlin.reflect.full.createInstance

private val logger = Logger.getLogger("es.magonxesp.pekorabot.discord.event.HandleCommandKt")

fun commandString(commandInfo: Command) = "$defaultCommandPrefix${commandInfo.command}"

fun MessageCreateEvent.getCommandInfo(command: KClass<*>): Command {
    val commandInfo = command.annotations
        .filterIsInstance(Command::class.java)
        .first()

    if (!message.content.startsWith(commandString(commandInfo))) {
        throw CommandException.Invalid("The command ${commandString(commandInfo)} is not a command")
    }

    return commandInfo
}

suspend fun MessageCreateEvent.evaluateCommandArguments(command: CommandHandler, commandInfo: Command): Map<String, Any?> {
    val channel = message.channel.awaitSingle()

    val messageParts = message.content
        .replaceFirst(commandString(commandInfo), "")
        .split(" ")
        .filter { it.isNotEmpty() }
        .toTypedArray()

    val arguments = mutableMapOf<String, Any?>()

    command.commandArguments.forEachIndexed { index, commandArgument ->
        val argument = if (index in messageParts.indices) messageParts[index] else null

        if (commandArgument.required && argument == null) {
            channel.createMessage("El argumento ${commandArgument.name} es obligatorio").awaitSingle()
            throw CommandException.MissingRequiredArgument("The required argument ${commandArgument.name} of command ${commandString(commandInfo)} is missing")
        }

        if (!commandArgument.required && argument == null && commandArgument.default != null) {
            arguments[commandArgument.name] = commandArgument.default
        } else {
            arguments[commandArgument.name] = if (argument != null) commandArgument.type.cast(argument) else null
        }
    }

    return arguments
}

suspend fun MessageCreateEvent.handleCommand(): Boolean {
    if (!message.content.startsWith(defaultCommandPrefix)) {
        return false
    }

    for (command in commands) {
        try {
            val commandInstance = command.createInstance()

            if (commandInstance !is CommandHandler) {
                continue
            }

            val commandInfo = getCommandInfo(command)
            val args = evaluateCommandArguments(commandInstance, commandInfo)
            commandInstance.handle(message = message, args = args)
            registerCommandFired()
        } catch (exception: Exception) {
            logger.log(Level.WARNING, exception.message, exception)
            continue
        }
    }

    return true
}
