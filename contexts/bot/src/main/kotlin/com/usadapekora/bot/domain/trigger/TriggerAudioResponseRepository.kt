package com.usadapekora.bot.domain.trigger

import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefault


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

interface TriggerAudioDefaultRepository : TriggerAudioResponseProviderRepository<TriggerAudioDefault>
