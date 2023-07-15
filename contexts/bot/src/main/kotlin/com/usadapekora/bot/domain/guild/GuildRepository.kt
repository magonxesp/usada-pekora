package com.usadapekora.bot.domain.guild

import arrow.core.Either
import com.usadapekora.bot.domain.guild.Guild.GuildId
import com.usadapekora.bot.domain.guild.Guild.GuildProviderId
import com.usadapekora.shared.domain.user.User

interface GuildRepository {
    fun find(id: GuildId): Either<GuildError.NotFound, Guild>
    fun findByProvider(providerId: GuildProviderId, provider: GuildProvider): Either<GuildError.NotFound, Guild>
    fun findByUserId(userId: User.UserId): Array<Guild>
    fun save(entity: Guild): Either<GuildError.SaveError, Unit>
    fun delete(entity: Guild): Either<GuildError.DeleteError, Unit>
}
