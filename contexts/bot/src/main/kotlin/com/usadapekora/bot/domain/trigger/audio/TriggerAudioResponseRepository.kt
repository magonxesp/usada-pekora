package com.usadapekora.bot.domain.trigger.audio

import com.usadapekora.bot.domain.trigger.Trigger


interface TriggerAudioResponseRepository {
    fun find(id: TriggerAudioResponseId, provider: TriggerAudioResponseProvider = TriggerAudioResponseProvider.DEFAULT): TriggerAudioResponse
    fun findByTrigger(id: Trigger.TriggerId, provider: TriggerAudioResponseProvider = TriggerAudioResponseProvider.DEFAULT): TriggerAudioResponse
}

interface TriggerAudioResponseProviderRepository<T : TriggerAudioResponse> {
    fun find(id: TriggerAudioResponseId): T
    fun findByTrigger(id: Trigger.TriggerId): T
    fun save(entity: T)
    fun delete(entity: T)
}

interface TriggerAudioDefaultRepository : TriggerAudioResponseProviderRepository<TriggerDefaultAudioResponse>
