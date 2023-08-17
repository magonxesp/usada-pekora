package com.usadapekora.bot.application.trigger.find.audio

import kotlinx.serialization.Serializable
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse as TriggerDefaultAudioResponseEntity

@Serializable
data class TriggerAudioResponseFindResponse(
    val id: String,
    val source: String,
    val sourceUri: String,
) {
    companion object {
        fun fromEntity(entity: TriggerDefaultAudioResponseEntity) = TriggerAudioResponseFindResponse(
            id = entity.id.value,
            source = entity.source.name.lowercase(),
            sourceUri = entity.sourceUri.value
        )
    }
}
