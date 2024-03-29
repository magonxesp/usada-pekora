package com.usadapekora.bot.application.guild.create

import arrow.core.Either
import arrow.core.left
import com.usadapekora.bot.application.guild.update.ProvidedGuildUpdateRequest
import com.usadapekora.bot.application.guild.update.ProvidedGuildUpdater
import com.usadapekora.bot.domain.guild.GuildException
import com.usadapekora.bot.domain.guild.GuildProvider
import com.usadapekora.bot.domain.guild.GuildProviderRepositoryFactory
import com.usadapekora.shared.domain.auth.AuthorizationGrantedEvent
import com.usadapekora.shared.domain.auth.OAuthUserRepository
import com.usadapekora.shared.domain.bus.event.DomainEventSubscriber
import com.usadapekora.shared.domain.bus.event.DomainEventSubscriberException
import com.usadapekora.shared.domain.bus.event.SubscribesDomainEvent
import com.usadapekora.shared.domain.user.User

@SubscribesDomainEvent(eventClass = AuthorizationGrantedEvent::class)
class CreateGuildsFromProviderOnAuthorizationGranted(
    private val oAuthUserRepository: OAuthUserRepository,
    private val guildProviderRepositoryFactory: GuildProviderRepositoryFactory,
    private val guildCreator: GuildCreator,
    private val guildMemberCreator: GuildMemberCreator,
    private val providedGuildUpdater: ProvidedGuildUpdater
) : DomainEventSubscriber<AuthorizationGrantedEvent> {

    override fun handle(event: AuthorizationGrantedEvent): Either<DomainEventSubscriberException, Unit> = Either.catch {
        val userId = User.UserId(event.userId)
        val user = oAuthUserRepository.find(userId)
            .onLeft { return DomainEventSubscriberException(it.message).left() }
            .getOrNull()!!

        val provider = Either.catch { GuildProvider.fromValue(user.provider) }
            .onLeft { return DomainEventSubscriberException(it.message).left() }
            .getOrNull()!!

        val repository = guildProviderRepositoryFactory.getInstance(provider, user.token)
            .onLeft { return DomainEventSubscriberException(it.message).left() }
            .getOrNull()!!

        val guilds = repository.findAll(userId)

        for (guild in guilds) {
            val createRequest = GuildCreateRequest(
                id = guild.id.value,
                providerId = guild.providerId.value,
                provider = guild.provider.value,
                name = guild.name.value,
                iconUrl = guild.iconUrl.value
            )

            val createMemberRequest = GuildMemberCreateRequest(
                userId = userId.value,
                guildId = guild.id.value
            )

            val createResult = guildCreator.create(createRequest).onRight {
                guildMemberCreator.create(createMemberRequest)
                    .onLeft { return DomainEventSubscriberException(it.message).left() }
            }

            if (createResult.leftOrNull() is GuildException.AlreadyExists) {
                providedGuildUpdater.update(ProvidedGuildUpdateRequest(
                    provider = guild.provider.value,
                    providerId = guild.providerId.value,
                    values = ProvidedGuildUpdateRequest.NewValues(
                        name = guild.name.value,
                        iconUrl = guild.iconUrl.value
                    )
                )).onLeft { return DomainEventSubscriberException(it.message).left() }
            }
        }
    }.mapLeft { DomainEventSubscriberException(it.message) }

}
