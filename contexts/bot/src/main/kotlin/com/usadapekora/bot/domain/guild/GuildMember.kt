package com.usadapekora.bot.domain.guild

import com.usadapekora.shared.domain.user.User.UserId
import com.usadapekora.bot.domain.guild.Guild.GuildId
import com.usadapekora.shared.domain.Entity

data class GuildMember(
    val user: UserId,
    val guild: GuildId
) : Entity() {
    companion object {
        fun fromPrimitives(user: String, guild: String) = GuildMember(
            user = UserId(user),
            guild = GuildId(guild)
        )
    }

    override fun id(): String = user.value
}
