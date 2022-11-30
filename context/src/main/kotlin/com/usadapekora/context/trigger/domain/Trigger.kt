package com.usadapekora.context.trigger.domain

import com.usadapekora.context.shared.domain.AggregateRoot
import com.usadapekora.context.shared.domain.valueobject.UuidValueObject

data class Trigger(
    val id: TriggerId,
    var input: TriggerInput,
    var compare: TriggerCompare,
    var outputText: TriggerOutputText,
    var discordGuildId: TriggerDiscordGuildId
) : AggregateRoot() {
    data class TriggerId(override val value: String) : UuidValueObject(value)
    data class TriggerInput(val value: String)
    data class TriggerOutputText(val value: String?)
    data class TriggerDiscordGuildId(val value: String)
    enum class TriggerCompare(val value: String) {
        In("in"),
        Pattern("pattern");

        companion object {
            fun fromValue(value: String): TriggerCompare {
                val instance = values().find {
                    it.value == value
                } ?: throw TriggerException.InvalidValue()

                return instance
            }
        }

        override fun toString(): String = value
    }

    companion object {
        fun fromPrimitives(
            id: String,
            input: String,
            compare: String,
            outputText: String?,
            discordGuildId: String
        ) = Trigger(
            id = TriggerId(id),
            input = TriggerInput(input),
            compare = TriggerCompare.fromValue(compare),
            outputText = TriggerOutputText(outputText),
            discordGuildId = TriggerDiscordGuildId(discordGuildId)
        )
    }
}
