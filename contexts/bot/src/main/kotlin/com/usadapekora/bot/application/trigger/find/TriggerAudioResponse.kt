package com.usadapekora.bot.application.trigger.find

import com.usadapekora.bot.domain.trigger.audio.TriggerDefaultAudioResponse

data class TriggerAudioResponse(
    val id: String,
    val triggerId: String,
    val guildId: String,
    val file: String
) {
    companion object {
        fun fromEntity(entity: TriggerDefaultAudioResponse) = TriggerAudioResponse(
            id = entity.id.value,
            triggerId = entity.trigger.value,
            guildId = entity.guild.value,
            file = entity.file.value
        )
    }
}
