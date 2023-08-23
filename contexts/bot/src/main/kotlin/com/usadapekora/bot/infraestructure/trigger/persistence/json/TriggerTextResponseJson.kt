package com.usadapekora.bot.infraestructure.trigger.persistence.json

import com.usadapekora.bot.domain.trigger.text.TriggerTextResponse
import kotlinx.serialization.Serializable

@Serializable
data class TriggerTextResponseJson(
    val id: String,
    val content: String,
    val type: String
) {
    fun toEntity() = TriggerTextResponse.fromPrimitives(
        id = id,
        content = content,
        type = type
    )
}
