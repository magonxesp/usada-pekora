package com.usadapekora.context.trigger.domain

interface TriggerRepository {
    suspend fun all(): Array<Trigger>
    suspend fun findByDiscordServer(id: String): Array<Trigger>
    suspend fun save(trigger: Trigger)
}
