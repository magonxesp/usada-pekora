package com.usadapekora.bot.application.trigger.find

import com.usadapekora.bot.domain.trigger.Trigger
import kotlinx.serialization.Serializable

@Serializable
data class TriggerResponse(
    val id: String,
    val title: String,
    val input: String,
    val compare: String,
    val responseTextId: String?,
    val responseAudioId: String?,
    val guildId: String?
) {
    companion object {
        fun fromEntity(entity: Trigger) = TriggerResponse(
            id = entity.id.value,
            title = entity.title.value,
            input = entity.input.value,
            compare = entity.compare.value,
            responseTextId = entity.responseText?.value,
            responseAudioId = entity.responseAudio?.value,
            guildId = entity.guildId?.value
        )
    }
}
