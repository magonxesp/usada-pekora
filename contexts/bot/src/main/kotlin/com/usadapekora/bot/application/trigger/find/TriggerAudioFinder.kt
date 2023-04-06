package com.usadapekora.bot.application.trigger.find

import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioDefault
import com.usadapekora.bot.domain.trigger.TriggerAudioResponseRepository

class TriggerAudioFinder(private val repository: TriggerAudioDefaultRepository) {

    fun find(id: String): TriggerAudioResponse
        = TriggerAudioResponse.fromEntity(repository.find(TriggerAudioResponseId(id)))

    fun findByTriggerId(triggerId: String): TriggerAudioResponse
        = TriggerAudioResponse.fromEntity(repository.findByTrigger(Trigger.TriggerId(triggerId)))

}
