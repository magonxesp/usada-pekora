package com.usadapekora.bot.domain.trigger

import arrow.core.Either

interface TriggerRepository {
    fun all(): Array<Trigger>
    fun find(id: Trigger.TriggerId): Either<TriggerException.NotFound, Trigger>
    fun findByDiscordServer(id: Trigger.TriggerDiscordGuildId): Array<Trigger>
    fun save(entity: Trigger)
    fun delete(entity: Trigger)
}
