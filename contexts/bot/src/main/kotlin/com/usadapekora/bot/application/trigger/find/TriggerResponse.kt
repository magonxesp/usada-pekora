package com.usadapekora.bot.application.trigger.find

import com.usadapekora.bot.domain.trigger.Trigger

data class TriggerResponse(
    val id: String,
    val title: String,
    val input: String,
    val compare: String,
    val outputText: String?,
    val discordGuildId: String
) {
    companion object {
        fun fromEntity(entity: Trigger) = TriggerResponse(
            id = entity.id.value,
            title = entity.title.value,
            input = entity.input.value,
            compare = entity.compare.value,
            outputText = entity.outputText.value,
            discordGuildId = entity.discordGuildId.value
        )
    }
}
