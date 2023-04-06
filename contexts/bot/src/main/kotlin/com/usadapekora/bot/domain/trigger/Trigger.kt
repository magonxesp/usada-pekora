package com.usadapekora.bot.domain.trigger

import com.usadapekora.bot.domain.shared.Entity
import com.usadapekora.bot.domain.shared.valueobject.UuidValueObject
import com.usadapekora.bot.domain.trigger.exception.TriggerException

data class Trigger(
    val id: TriggerId,
    var title: TriggerTitle,
    var input: TriggerInput,
    var compare: TriggerCompare,
    var responseText: TriggerTextResponse?,
    var responseAudio: TriggerAudioResponse?,
    var discordGuildId: TriggerDiscordGuildId
) : Entity() {
    data class TriggerId(override val value: String) : UuidValueObject(value)

    data class TriggerTitle(val value: String) {
        init {
            if (value.isBlank()) {
                throw TriggerException.InvalidValue("The title can't be a empty string")
            }
        }
    }

    data class TriggerInput(val value: String) {
        init {
            if (value.isBlank()) {
                throw TriggerException.InvalidValue("The input can't be a empty string")
            }
        }
    }

    data class TriggerDiscordGuildId(val value: String) {
        init {
            if (value.isBlank()) {
                throw TriggerException.InvalidValue("The discordGuildId can't be a empty string")
            }
        }
    }

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
            title: String,
            input: String,
            compare: String,
            discordGuildId: String,
            responseText: TriggerTextResponse? = null,
            responseAudio: TriggerAudioResponse? = null,
        ) = Trigger(
            id = TriggerId(id),
            title = TriggerTitle(title),
            input = TriggerInput(input),
            compare = TriggerCompare.fromValue(compare),
            responseText = responseText,
            responseAudio = responseAudio,
            discordGuildId = TriggerDiscordGuildId(discordGuildId)
        )
    }

    override fun id(): String = id.value
}
