package com.usadapekora.bot.application.guild.create

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.guild.Guild.GuildId
import com.usadapekora.bot.domain.guild.Guild.GuildProviderId
import com.usadapekora.bot.domain.guild.GuildException
import com.usadapekora.bot.domain.guild.GuildProvider
import com.usadapekora.bot.domain.guild.GuildRepository
import com.usadapekora.shared.domain.PersistenceTransaction
import com.usadapekora.shared.domain.bus.event.DomainEventBus
import com.usadapekora.shared.domain.guild.GuildCreatedEvent

class GuildCreator(
    private val repository: GuildRepository,
    private val persistenceTransaction: PersistenceTransaction,
    private val eventBus: DomainEventBus
) {
    fun create(request: GuildCreateRequest): Either<GuildException, Unit> {
        repository.find(GuildId(request.id))
            .onRight { return GuildException.AlreadyExists("The guild with id ${request.id} already exists").left() }

        repository.findByProvider(GuildProviderId(request.providerId), GuildProvider.fromValue(request.provider))
            .onRight { return GuildException.AlreadyExists("The guild with provider id ${request.providerId} by provider ${request.provider} already exists")
                .left() }

        val guild = Guild.fromPrimitives(
            id = request.id,
            name = request.name,
            iconUrl = request.iconUrl,
            providerId = request.providerId,
            provider = request.provider
        )

        persistenceTransaction.start()

        repository.save(guild).onLeft { return it.left() }

        eventBus.dispatch(GuildCreatedEvent(guildId = guild.id.value)).onLeft {
            persistenceTransaction.rollback()
            return GuildException.SaveError(it.message).left()
        }

        persistenceTransaction.commit()
        return Unit.right()
    }

}
