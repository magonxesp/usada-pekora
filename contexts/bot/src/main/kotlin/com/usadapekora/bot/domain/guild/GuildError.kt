package com.usadapekora.bot.domain.guild

sealed class GuildError(open val message: String? = null) {
    class SaveError(override val message: String? = null) : GuildError(message = message)
    class NotFound(override val message: String? = null) : GuildError(message = message)
    class DeleteError(override val message: String? = null) : GuildError(message = message)
    class AlreadyExists(override val message: String? = null) : GuildError(message = message)
}
