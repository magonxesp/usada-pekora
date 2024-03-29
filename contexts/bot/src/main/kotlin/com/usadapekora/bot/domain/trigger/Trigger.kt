package com.usadapekora.bot.domain.trigger

import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseId
import com.usadapekora.shared.domain.Entity
import com.usadapekora.shared.domain.valueobject.UuidValueObject

data class Trigger(
    val id: TriggerId,
    var title: TriggerTitle,
    var input: TriggerInput,
    var compare: TriggerCompare,
    var kind: TriggerKind,
    var responseText: TriggerTextResponseId?,
    var responseAudio: TriggerAudioResponseId?,
    var guildId: Guild.GuildId?
) : Entity() {
    data class TriggerId(override val value: String) : UuidValueObject(value) {
        init {
            validate()
        }
    }

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
            kind: String,
            guildId: String?,
            responseTextId: String? = null,
            responseAudioId: String? = null,
        ) = Trigger(
            id = TriggerId(id),
            title = TriggerTitle(title),
            input = TriggerInput(input),
            compare = TriggerCompare.fromValue(compare),
            kind = TriggerKind.fromValue(kind),
            responseText = responseTextId?.let { TriggerTextResponseId(it) },
            responseAudio = responseAudioId?.let { TriggerAudioResponseId(it) },
            guildId = guildId?.let { Guild.GuildId(it) }
        )
    }

    init {
        if (responseAudio == null && responseText == null) {
            throw TriggerException.MissingResponse("The trigger should have at least one response")
        }
    }

    override fun id(): String = id.value
}
