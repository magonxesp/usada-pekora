package com.usadapekora.bot.application.trigger.find

import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerAudio
import com.usadapekora.bot.domain.trigger.TriggerAudioRepository

class TriggerAudioFinder(private val repository: TriggerAudioRepository) {

    fun find(id: String): TriggerAudioResponse
        = TriggerAudioResponse.fromEntity(repository.find(TriggerAudio.TriggerAudioId(id)))

    fun findByTriggerId(triggerId: String): TriggerAudioResponse
        = TriggerAudioResponse.fromEntity(repository.findByTrigger(Trigger.TriggerId(triggerId)))

}
