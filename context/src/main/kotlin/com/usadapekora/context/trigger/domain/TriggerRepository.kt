package com.usadapekora.context.trigger.domain

interface TriggerRepository {
    fun all(): Array<Trigger>
    fun find(id: String): Trigger
    fun findByDiscordServer(id: String): Array<Trigger>
    fun save(trigger: Trigger)
    fun delete(trigger: Trigger)
}
