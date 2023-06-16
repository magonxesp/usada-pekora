package com.usadapekora.bot.domain.guild

sealed class GuildMemberError(open val message: String? = null) {
    class SaveError(override val message: String? = null) : GuildMemberError(message = message)
    class NotFound(override val message: String? = null) : GuildMemberError(message = message)
    class DeleteError(override val message: String? = null) : GuildMemberError(message = message)
    class AlreadyExists(override val message: String? = null) : GuildMemberError(message = message)
}
