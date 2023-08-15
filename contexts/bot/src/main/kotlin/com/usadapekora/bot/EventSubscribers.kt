package com.usadapekora.bot

import com.usadapekora.bot.application.guild.create.CreateGuildsFromProviderOnAuthorizationGranted
import com.usadapekora.bot.application.trigger.create.CreateTriggerOnGuildCreate
import com.usadapekora.shared.EventSubscribers

val eventSubscribers: EventSubscribers = arrayOf(
    CreateGuildsFromProviderOnAuthorizationGranted::class,
    CreateTriggerOnGuildCreate::class
)
