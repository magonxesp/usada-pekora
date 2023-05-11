package com.usadapekora.bot.application.trigger.find.audio

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseId

class TriggerDefaultAudioFinder(private val repository: TriggerAudioDefaultRepository) {

    fun find(id: String): Either<TriggerAudioResponseException, TriggerDefaultAudioFindResponse> {
        val result = repository.find(TriggerAudioResponseId(id))

        if (result.isLeft()) {
            return result.leftOrNull()!!.left()
        }

        return TriggerDefaultAudioFindResponse.fromEntity(result.getOrNull()!!).right()
    }

    fun findByTriggerId(triggerId: String): Either<TriggerAudioResponseException, TriggerDefaultAudioFindResponse> {
        val result = repository.findByTrigger(Trigger.TriggerId(triggerId))

        if (result.isLeft()) {
            return result.leftOrNull()!!.left()
        }

        return TriggerDefaultAudioFindResponse.fromEntity(result.getOrNull()!!).right()
    }
}
