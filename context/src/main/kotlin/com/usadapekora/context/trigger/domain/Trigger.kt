package com.usadapekora.context.trigger.domain

import com.usadapekora.context.shared.domain.AggregateRoot

class Trigger(
    val id: String,
    val input: String,
    val compare: TriggerCompare,
    val outputText: String?,
    val outputSound: String?,
    val discordGuildId: String
) : AggregateRoot() {
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
            outputSound: String?,
            discordGuildId: String
        ) = Trigger(
            id = id,
            input = input,
            compare = TriggerCompare.fromValue(compare),
            outputText = outputText,
            outputSound = outputSound,
            discordGuildId = discordGuildId
        )
    }
}
