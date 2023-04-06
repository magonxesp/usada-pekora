package com.usadapekora.bot.domain.trigger.response.audio

import com.usadapekora.bot.domain.guild.GuildId
import com.usadapekora.bot.domain.shared.Entity
import com.usadapekora.bot.domain.shared.valueobject.UuidValueObject
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerAudioProvider
import com.usadapekora.bot.domain.trigger.TriggerAudioResponse
import com.usadapekora.bot.domain.trigger.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.utils.TriggerAudioUtils

data class TriggerAudioDefault(
    val id: TriggerAudioResponseId,
    val trigger: Trigger.TriggerId,
    val guild: GuildId,
    val file: TriggerAudioFile
) : Entity(), TriggerAudioResponse {
    data class TriggerAudioFile(val value: String)

    companion object {
        fun fromPrimitives(
            id: String,
            trigger: String,
            guild: String,
            file: String
        ): TriggerAudioDefault = TriggerAudioDefault(
            id = TriggerAudioResponseId(id),
            trigger = Trigger.TriggerId(trigger),
            guild = GuildId(guild),
            file = TriggerAudioFile(file)
        )
    }

    override fun id(): String = id.value

    override fun path() = TriggerAudioUtils.audioDirPath(this)

    override fun provider() = TriggerAudioProvider.DEFAULT
}
