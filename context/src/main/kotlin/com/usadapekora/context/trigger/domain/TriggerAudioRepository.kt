package com.usadapekora.context.trigger.domain

interface TriggerAudioRepository {
    fun find(id: TriggerAudio.TriggerAudioId): TriggerAudio
    fun findByTrigger(id: Trigger.TriggerId): TriggerAudio
    fun save(audio: TriggerAudio)
    fun delete(audio: TriggerAudio)
}
