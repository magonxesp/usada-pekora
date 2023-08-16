package com.usadapekora.bot.domain.trigger.audio

import arrow.core.Either
import com.usadapekora.bot.domain.trigger.Trigger

interface TriggerAudioResponseRepository {
    fun find(id: TriggerAudioResponseId): Either<TriggerAudioResponseException, TriggerAudioResponse>
    fun findByTrigger(id: Trigger.TriggerId): Either<TriggerAudioResponseException, TriggerAudioResponse>
    fun save(entity: TriggerAudioResponse)
    fun delete(entity: TriggerAudioResponse)
}
