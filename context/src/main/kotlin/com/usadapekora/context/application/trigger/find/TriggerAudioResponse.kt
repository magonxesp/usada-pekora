package com.usadapekora.context.application.trigger.find

import com.usadapekora.context.domain.trigger.TriggerAudio

data class TriggerAudioResponse(
    val id: String,
    val triggerId: String,
    val guildId: String,
    val file: String
) {
    companion object {
        fun fromEntity(entity: TriggerAudio) = TriggerAudioResponse(
            id = entity.id.value,
            triggerId = entity.trigger.value,
            guildId = entity.guild.value,
            file = entity.file.value
        )
    }
}
