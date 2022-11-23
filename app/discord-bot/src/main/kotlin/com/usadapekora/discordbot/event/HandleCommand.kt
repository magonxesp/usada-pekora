package com.usadapekora.discordbot.event

import discord4j.core.event.domain.message.MessageCreateEvent
import com.usadapekora.discordbot.shared.annotation.Command
import com.usadapekora.discordbot.commands
import com.usadapekora.discordbot.defaultCommandPrefix
import com.usadapekora.discordbot.shared.CommandHandler
import com.usadapekora.discordbot.shared.exception.CommandException
import com.usadapekora.context.shared.infraestructure.prometheus.registerCommandFired
import kotlinx.coroutines.reactor.awaitSingle
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.reflect.KClass
import kotlin.reflect.full.cast
import kotlin.reflect.full.createInstance

private val logger = Logger.getLogger("com.pekorabot.discord.event.HandleCommandKt")

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
