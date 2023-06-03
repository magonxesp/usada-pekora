package com.usadapekora.bot.application.trigger.find.audio

import kotlinx.serialization.Serializable
import com.usadapekora.bot.domain.trigger.audio.TriggerDefaultAudioResponse as TriggerDefaultAudioResponseEntity

@Serializable
data class TriggerDefaultAudioFindResponse(
    val id: String,
    val triggerId: String,
    val guildId: String,
    val file: String
) {
    companion object {
        fun fromEntity(entity: TriggerDefaultAudioResponseEntity) = TriggerDefaultAudioFindResponse(
            id = entity.id.value,
            triggerId = entity.trigger.value,
            guildId = entity.guild.value,
            file = entity.file.value
        )
    }
}
