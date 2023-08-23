package com.usadapekora.bot.infraestructure.trigger.persistence.json

import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import kotlinx.serialization.Serializable

@Serializable
data class TriggerAudioResponseJson(
    val id: String,
    val fileName: String
) {
    fun toEntity() = TriggerAudioResponse.fromPrimitives(
        id = id,
        kind = TriggerAudioResponse.TriggerAudioResponseKind.RESOURCE.name,
        source = "/$fileName"
    )
}
