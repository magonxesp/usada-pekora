package com.usadapekora.discordbot.shared.exception

sealed class CommandException(override val message: String? = null) : Exception(message) {
    class Invalid(override val message: String? = null) : CommandException(message)
    class MissingRequiredArgument(override val message: String? = null) : CommandException(message)
}
