package com.usadapekora.context.trigger.domain

interface TriggerRepository {
    fun all(): Array<Trigger>
    fun find(id: Trigger.TriggerId): Trigger
    fun findByDiscordServer(id: Trigger.TriggerDiscordGuildId): Array<Trigger>
    fun save(trigger: Trigger)
    fun delete(trigger: Trigger)
}
