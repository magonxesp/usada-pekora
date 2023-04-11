package com.usadapekora.bot.domain.trigger.text

import com.usadapekora.bot.domain.guild.GuildId
import com.usadapekora.bot.domain.shared.Entity
import com.usadapekora.bot.domain.trigger.Trigger

data class TriggerTextResponse(
    val id: TriggerTextResponseId,
    var content: TriggerTextContent,
    val type: TriggerContentType,
    val triggerId: Trigger.TriggerId,
    val discordGuildId: GuildId
) : Entity() {
    data class TriggerTextContent(val value: String)

    companion object {
        fun fromPrimitives(
            id: String,
            content: String,
            type: String,
            triggerId: String,
            discordGuildId: String
        ) = TriggerTextResponse(
            id = TriggerTextResponseId(id),
            content = TriggerTextContent(content),
            type = TriggerContentType.fromValue(type),
            triggerId = Trigger.TriggerId(triggerId),
            discordGuildId = GuildId(discordGuildId)
        )
    }

    override fun id() = id.value
}
