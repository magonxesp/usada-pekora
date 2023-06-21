package com.usadapekora.bot.domain.trigger.audio

import arrow.core.Either
import com.usadapekora.bot.domain.trigger.Trigger


interface TriggerAudioResponseRepository {
    fun find(
        id: TriggerAudioResponseId,
        provider: TriggerAudioResponseProvider = TriggerAudioResponseProvider.DEFAULT
    ): Either<TriggerAudioResponseException, TriggerAudioResponse>

    fun findByTrigger(
        id: Trigger.TriggerId,
        provider: TriggerAudioResponseProvider = TriggerAudioResponseProvider.DEFAULT
    ): Either<TriggerAudioResponseException, TriggerAudioResponse>
}

interface TriggerAudioResponseProviderRepository<T : TriggerAudioResponse> {
    fun find(id: TriggerAudioResponseId): Either<TriggerAudioResponseException, T>
    fun findByTrigger(id: Trigger.TriggerId): Either<TriggerAudioResponseException, T>
    fun save(entity: T)
    fun delete(entity: T)
}

interface TriggerAudioDefaultRepository : TriggerAudioResponseProviderRepository<TriggerDefaultAudioResponse>
