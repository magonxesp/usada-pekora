package com.usadapekora.bot.application.guild.create

import arrow.core.Either
import com.usadapekora.shared.domain.auth.AuthorizationGrantedEvent
import com.usadapekora.shared.domain.bus.event.EventSubscriber
import com.usadapekora.shared.domain.bus.event.EventSubscriberError
import com.usadapekora.shared.domain.bus.event.SubscribesEvent

@SubscribesEvent<AuthorizationGrantedEvent>(eventClass = AuthorizationGrantedEvent::class)
class CreateGuildsFromProviderOnAuthorizationGranted : EventSubscriber<AuthorizationGrantedEvent> {

    override fun handle(event: AuthorizationGrantedEvent): Either<EventSubscriberError, Unit> {
        TODO("Not yet implemented")
        // llamar a OAuthUserRepository y recoger el oauth user por user id que viene en el evento
        // llamar a GuildProviderRepositoryFactory y pasarle el oauth user que contiene el token
    }

}
