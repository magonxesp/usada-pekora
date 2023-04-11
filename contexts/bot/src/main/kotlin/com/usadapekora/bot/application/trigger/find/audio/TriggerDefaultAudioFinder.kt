package com.usadapekora.bot.application.trigger.find.audio

import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseId

class TriggerDefaultAudioFinder(private val repository: TriggerAudioDefaultRepository) {

    fun find(id: String): TriggerDefaultAudioFindResponse
        = TriggerDefaultAudioFindResponse.fromEntity(repository.find(TriggerAudioResponseId(id)))

    fun findByTriggerId(triggerId: String): TriggerDefaultAudioFindResponse
        = TriggerDefaultAudioFindResponse.fromEntity(repository.findByTrigger(Trigger.TriggerId(triggerId)))

}
