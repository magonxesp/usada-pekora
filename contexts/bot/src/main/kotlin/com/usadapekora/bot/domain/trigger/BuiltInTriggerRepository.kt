package com.usadapekora.bot.domain.trigger

import arrow.core.Either

interface BuiltInTriggerRepository {
    fun findAll(): Array<Trigger>
    fun find(id: Trigger.TriggerId): Either<TriggerException.NotFound, Trigger>
}
