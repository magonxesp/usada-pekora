package com.usadapekora.context.application.trigger.find

import com.usadapekora.context.domain.trigger.Trigger
import com.usadapekora.context.domain.trigger.TriggerAudio
import com.usadapekora.context.domain.trigger.TriggerAudioRepository

class TriggerAudioFinder(private val repository: TriggerAudioRepository) {

    fun find(id: String): TriggerAudioResponse
        = TriggerAudioResponse.fromEntity(repository.find(TriggerAudio.TriggerAudioId(id)))

    fun findByTriggerId(triggerId: String): TriggerAudioResponse
        = TriggerAudioResponse.fromEntity(repository.findByTrigger(Trigger.TriggerId(triggerId)))

}
