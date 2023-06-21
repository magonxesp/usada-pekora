package com.usadapekora.bot.application.guild.create

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.guild.Guild.GuildId
import com.usadapekora.bot.domain.guild.Guild.GuildProviderId
import com.usadapekora.bot.domain.guild.GuildError
import com.usadapekora.bot.domain.guild.GuildProvider
import com.usadapekora.bot.domain.guild.GuildRepository

class GuildCreator(private val repository: GuildRepository) {
    fun create(request: GuildCreateRequest): Either<GuildError, Unit> {
        repository.find(GuildId(request.id))
            .onRight { return GuildError.AlreadyExists("The guild with id ${request.id} already exists").left() }

        repository.findByProvider(GuildProviderId(request.providerId), GuildProvider.fromValue(request.provider))
            .onRight { return GuildError.AlreadyExists("The guild with provider id ${request.providerId} by provider ${request.provider} already exists").left() }

        val guild = Guild.fromPrimitives(
            id = request.id,
            name = request.name,
            iconUrl = request.iconUrl,
            providerId = request.providerId,
            provider = request.provider
        )

        return repository.save(guild)
            .onLeft { return it.left() }
            .getOrNull()!!.right()
    }

}
