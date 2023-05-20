package com.usadapekora.bot.domain.trigger.text

import com.usadapekora.shared.domain.common.Entity

data class TriggerTextResponse(
    val id: TriggerTextResponseId,
    var content: TriggerTextResponseContent,
    var type: TriggerTextResponseContentType
) : Entity() {
    data class TriggerTextResponseContent(val value: String)

    companion object {
        fun fromPrimitives(
            id: String,
            content: String,
            type: String
        ) = TriggerTextResponse(
            id = TriggerTextResponseId(id),
            content = TriggerTextResponseContent(content),
            type = TriggerTextResponseContentType.fromValue(type)
        )
    }

    override fun id() = id.value
}
