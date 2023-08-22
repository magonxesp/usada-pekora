package com.usadapekora.bot.application.trigger.find.audio

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository

class TriggerAudioResponseFinder(private val repository: TriggerAudioResponseRepository) {

    fun find(id: String): Either<TriggerAudioResponseException, TriggerAudioResponseFindResponse> {
        val result = repository.find(TriggerAudioResponseId(id))

        if (result.isLeft()) {
            return result.leftOrNull()!!.left()
        }

        return TriggerAudioResponseFindResponse.fromEntity(result.getOrNull()!!).right()
    }

    fun findByTriggerId(triggerId: String): Either<TriggerAudioResponseException, TriggerAudioResponseFindResponse> {
        val result = repository.findByTrigger(Trigger.TriggerId(triggerId))

        if (result.isLeft()) {
            return result.leftOrNull()!!.left()
        }

        return TriggerAudioResponseFindResponse.fromEntity(result.getOrNull()!!).right()
    }
}
