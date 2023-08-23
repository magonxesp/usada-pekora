package com.usadapekora.bot.infraestructure.trigger.persistence.json

import com.usadapekora.bot.domain.trigger.Trigger
import kotlinx.serialization.Serializable

@Serializable
class TriggerJson(
    val id: String,
    val title: String,
    val input: String,
    val compare: String,
    val responseText: TriggerTextResponseJson? = null,
    val responseAudio: TriggerAudioResponseJson? = null,
) {
    fun toEntity() = Trigger.fromPrimitives(
        id = id,
        title = title,
        input = input,
        compare = compare,
        kind = "built_in",
        responseTextId = responseText?.id,
        responseAudioId = responseAudio?.id,
        guildId = null,
    )
}
