package com.usadapekora.bot.domain.guild

sealed class GuildProviderException(override val message: String? = null) : Exception(message) {
    class NotFound(override val message: String? = null) : GuildProviderException(message = message)
}
