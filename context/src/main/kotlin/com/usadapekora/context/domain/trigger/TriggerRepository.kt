package com.usadapekora.context.domain.trigger

interface TriggerRepository {
    fun all(): Array<Trigger>
    fun find(id: Trigger.TriggerId): Trigger
    fun findByDiscordServer(id: Trigger.TriggerDiscordGuildId): Array<Trigger>
    fun save(entity: Trigger)
    fun delete(entity: Trigger)
}
