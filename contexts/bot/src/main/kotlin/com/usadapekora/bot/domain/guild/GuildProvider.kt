package com.usadapekora.bot.domain.guild

enum class GuildProvider(val value: String) {
    DISCORD("discord");

    companion object {
        fun fromValue(value: String)
            = values().firstOrNull { it.value == value }
            ?: throw GuildException.InvalidGuildProvider("The guild provider $value is invalid")
    }
}
