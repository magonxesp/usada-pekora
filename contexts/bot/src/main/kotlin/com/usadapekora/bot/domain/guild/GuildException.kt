package com.usadapekora.bot.domain.guild

sealed class GuildException(override val message: String? = null) : Exception(message){
    class InvalidGuildProvider(override val message: String? = null) : GuildException(message = message)
    class SaveError(override val message: String? = null) : GuildException(message = message)
    class NotFound(override val message: String? = null) : GuildException(message = message)
    class DeleteError(override val message: String? = null) : GuildException(message = message)
    class AlreadyExists(override val message: String? = null) : GuildException(message = message)
}
