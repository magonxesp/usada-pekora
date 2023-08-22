package com.usadapekora.bot.application.trigger.find.audio

import kotlinx.serialization.Serializable
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse as TriggerDefaultAudioResponseEntity

@Serializable
data class TriggerAudioResponseFindResponse(
    val id: String,
    val kind: String,
    val source: String,
) {
    companion object {
        fun fromEntity(entity: TriggerDefaultAudioResponseEntity) = TriggerAudioResponseFindResponse(
            id = entity.id.value,
            kind = entity.kind.name.lowercase(),
            source = entity.source.value
        )
    }
}
