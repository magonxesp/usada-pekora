package com.usadapekora.bot

import com.usadapekora.bot.application.guild.create.CreateGuildsFromProviderOnAuthorizationGranted
import com.usadapekora.bot.application.trigger.create.CreateTriggerOnGuildCreate
import com.usadapekora.shared.DomainEventSubscribers
import com.usadapekora.shared.DomainEvents
import com.usadapekora.shared.domain.auth.AuthorizationGrantedEvent
import com.usadapekora.shared.domain.guild.GuildCreatedEvent

val events: DomainEvents = mapOf(
    "auth.authorization_granted" to AuthorizationGrantedEvent::class,
    "guild.created" to GuildCreatedEvent::class
)

val eventSubscribers: DomainEventSubscribers = mapOf(
    "auth.authorization_granted" to arrayOf(
        CreateGuildsFromProviderOnAuthorizationGranted::class,
    ),
    "guild.created" to arrayOf(
        CreateTriggerOnGuildCreate::class
    )
)
