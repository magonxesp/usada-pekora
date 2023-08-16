package com.usadapekora.bot.application.trigger.find.audio

import kotlinx.serialization.Serializable
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse as TriggerDefaultAudioResponseEntity

@Serializable
data class TriggerAudioResponseFindResponse(
    val id: String,
    val triggerId: String,
    val guildId: String,
    val file: String
) {
    companion object {
        fun fromEntity(entity: TriggerDefaultAudioResponseEntity) = TriggerAudioResponseFindResponse(
            id = entity.id.value,
            triggerId = entity.trigger.value,
            guildId = entity.guild.value,
            file = entity.sourceUri.value
        )
    }
}
