package com.usadapekora.context.domain.guild

sealed class GuildPreferencesException(override val message: String? = null) : Exception(message) {
    class NotFound(override val message: String? = null) : GuildPreferencesException(message)
}
