package com.usadapekora.bot.domain.guild

sealed class GuildProviderError(open val message: String? = null) {
    class NotFound(override val message: String? = null) : GuildProviderError(message = message)
}
