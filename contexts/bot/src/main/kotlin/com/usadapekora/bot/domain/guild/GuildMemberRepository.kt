package com.usadapekora.bot.domain.guild

import arrow.core.Either
import com.usadapekora.bot.domain.guild.Guild.GuildId
import com.usadapekora.shared.domain.user.User.UserId

interface GuildMemberRepository {
    fun find(user: UserId, guild: GuildId): Either<GuildMemberException.NotFound, GuildMember>
    fun findByUser(user: UserId): Array<GuildMember>
    fun save(entity: GuildMember): Either<GuildMemberException.SaveError, Unit>
    fun delete(entity: GuildMember): Either<GuildMemberException.DeleteError, Unit>
}
