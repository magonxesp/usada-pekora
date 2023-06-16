package com.usadapekora.bot.domain.guild

sealed class GuildException(override val message: String? = null) : Exception(message){
    class InvalidGuildProvider(override val message: String? = null) : GuildException(message = message)
}
