package com.usadapekora.bot.domain.trigger.audio

import com.usadapekora.bot.domain.guild.GuildId
import com.usadapekora.bot.domain.shared.Entity
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.utils.TriggerAudioUtils
import com.usadapekora.bot.storageDirPath
import java.io.File
import kotlin.io.path.Path

data class TriggerDefaultAudioResponse(
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
        ): TriggerDefaultAudioResponse = TriggerDefaultAudioResponse(
            id = TriggerAudioResponseId(id),
            trigger = Trigger.TriggerId(trigger),
            guild = GuildId(guild),
            file = TriggerAudioFile(file)
        )
    }

    override fun id(): String = id.value
    override val path: String = Path(storageDirPath, "trigger", "audio", guild.value, trigger.value, "${id.value}.${File(file.value).extension}").toString()
    override val provider = TriggerAudioResponseProvider.DEFAULT
}
