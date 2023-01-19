package com.usadapekora.context.application.trigger.find

import com.usadapekora.context.domain.trigger.TriggerAudio
import com.usadapekora.context.domain.trigger.TriggerAudioRepository

class TriggerAudioFinder(private val repository: TriggerAudioRepository) {

    fun find(id: String): TriggerAudioResponse {
        val audio = repository.find(TriggerAudio.TriggerAudioId(id))
        return TriggerAudioResponse.fromEntity(audio)
    }

}
