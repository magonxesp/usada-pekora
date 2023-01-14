package com.usadapekora.context.domain.trigger

interface TriggerAudioRepository {
    fun find(id: TriggerAudio.TriggerAudioId): TriggerAudio
    fun findByTrigger(id: Trigger.TriggerId): TriggerAudio
    fun save(entity: TriggerAudio)
    fun delete(entity: TriggerAudio)
}
