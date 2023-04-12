package com.usadapekora.bot.domain.trigger.text

import com.usadapekora.bot.domain.shared.Entity

data class TriggerTextResponse(
    val id: TriggerTextResponseId,
    var content: TriggerTextContent,
    val type: TriggerContentType
) : Entity() {
    data class TriggerTextContent(val value: String)

    companion object {
        fun fromPrimitives(
            id: String,
            content: String,
            type: String
        ) = TriggerTextResponse(
            id = TriggerTextResponseId(id),
            content = TriggerTextContent(content),
            type = TriggerContentType.fromValue(type)
        )
    }

    override fun id() = id.value
}
