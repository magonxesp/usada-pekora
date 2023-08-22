package com.usadapekora.bot.domain.trigger.audio

import arrow.core.Either
import com.usadapekora.bot.domain.trigger.Trigger

interface TriggerAudioResponseRepository {
    fun find(id: TriggerAudioResponse.TriggerAudioResponseId): Either<TriggerAudioResponseException.NotFound, TriggerAudioResponse>
    fun findByTrigger(id: Trigger.TriggerId): Either<TriggerAudioResponseException.NotFound, TriggerAudioResponse>
    fun save(entity: TriggerAudioResponse)
    fun delete(entity: TriggerAudioResponse)
}
