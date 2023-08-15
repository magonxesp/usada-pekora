package com.usadapekora.bot.application.trigger.create

import arrow.core.Either
import com.usadapekora.shared.domain.bus.event.EventSubscriber
import com.usadapekora.shared.domain.bus.event.EventSubscriberError
import com.usadapekora.shared.domain.bus.event.SubscribesEvent
import com.usadapekora.shared.domain.guild.GuildCreatedEvent

@SubscribesEvent<GuildCreatedEvent>(eventClass = GuildCreatedEvent::class)
class CreateTriggerOnGuildCreate(
    private val triggerCreator: TriggerCreator
) : EventSubscriber<GuildCreatedEvent> {
    override fun handle(event: GuildCreatedEvent): Either<EventSubscriberError, Unit> {
        TODO("implement")
    }
}
