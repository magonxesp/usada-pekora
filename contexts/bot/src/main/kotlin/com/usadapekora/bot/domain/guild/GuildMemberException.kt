package com.usadapekora.bot.domain.guild

sealed class GuildMemberException(override val message: String? = null) : Exception(message) {
    class SaveError(override val message: String? = null) : GuildMemberException(message = message)
    class NotFound(override val message: String? = null) : GuildMemberException(message = message)
    class DeleteError(override val message: String? = null) : GuildMemberException(message = message)
    class AlreadyExists(override val message: String? = null) : GuildMemberException(message = message)
}
