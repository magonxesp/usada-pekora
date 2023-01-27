package com.usadapekora.context.domain.trigger

import com.usadapekora.context.domain.guild.GuildId
import com.usadapekora.context.domain.shared.Entity
import com.usadapekora.context.domain.shared.valueobject.UuidValueObject

data class TriggerAudio(
    val id: TriggerAudioId,
    val trigger: Trigger.TriggerId,
    val guild: GuildId,
    val file: TriggerAudioFile
) : Entity() {
    data class TriggerAudioId(override val value: String) : UuidValueObject(value)
    data class TriggerAudioFile(val value: String)

    companion object {
        fun fromPrimitives(
            id: String,
            trigger: String,
            guild: String,
            file: String
        ): TriggerAudio = TriggerAudio(
            id = TriggerAudioId(id),
            trigger = Trigger.TriggerId(trigger),
            guild = GuildId(guild),
            file = TriggerAudioFile(file)
        )
    }

    override fun id(): String = id.value
}
