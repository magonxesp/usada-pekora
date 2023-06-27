package com.usadapekora.bot.application.guild.update

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.guild.GuildError
import com.usadapekora.bot.domain.guild.GuildProvider
import com.usadapekora.bot.domain.guild.GuildRepository

class ProvidedGuildUpdater(private val repository: GuildRepository) {

    fun update(request: ProvidedGuildUpdateRequest): Either<GuildError, Unit> {
        val provider = GuildProvider.fromValue(request.provider)
        val providerId = Guild.GuildProviderId(request.providerId)

        val guild = repository.findByProvider(providerId, provider)
            .onLeft { return it.left() }
            .getOrNull()!!

        request.values.name?.let {
            guild.name = Guild.GuildName(it)
        }

        request.values.iconUrl?.let {
            guild.iconUrl = Guild.GuildIconUrl(it)
        }

        return repository.save(guild)
            .onLeft { return it.left() }
            .getOrNull()!!.right()
    }

}
