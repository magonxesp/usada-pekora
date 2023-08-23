package com.usadapekora.bot.domain.trigger.audio

import arrow.core.Either
import com.usadapekora.bot.domain.trigger.Trigger

interface BuiltInTriggerAudioResponseRepository {
    fun find(id: TriggerAudioResponse.TriggerAudioResponseId): Either<TriggerAudioResponseException.NotFound, TriggerAudioResponse>
}
