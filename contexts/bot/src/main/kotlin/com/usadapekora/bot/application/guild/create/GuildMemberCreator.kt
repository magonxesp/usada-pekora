package com.usadapekora.bot.application.guild.create

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild.GuildId
import com.usadapekora.bot.domain.guild.GuildMember
import com.usadapekora.bot.domain.guild.GuildMemberException
import com.usadapekora.bot.domain.guild.GuildMemberRepository
import com.usadapekora.shared.domain.user.User.UserId

class GuildMemberCreator(private val repository: GuildMemberRepository) {
    fun create(request: GuildMemberCreateRequest): Either<GuildMemberException, Unit> {
        repository.find(UserId(request.userId), GuildId(request.guildId))
            .onRight { return GuildMemberException.AlreadyExists("The user ${request.userId} is already member of guild ${request.guildId}").left() }

        return repository.save(GuildMember.fromPrimitives(user = request.userId, guild = request.guildId))
            .onLeft { return it.left() }
            .getOrNull()!!.right()
    }
}
