package com.usadapekora.context.domain.trigger

import com.usadapekora.context.domain.guild.GuildId
import com.usadapekora.context.domain.shared.Entity
import com.usadapekora.context.domain.shared.valueobject.UuidValueObject

data class TriggerAudio(
    val id: TriggerAudioId,
    val trigger: Trigger.TriggerId,
    val guild: GuildId,
) : Entity() {
    data class TriggerAudioId(override val value: String) : UuidValueObject(value)

    companion object {
        fun fromPrimitives(
            id: String,
            trigger: String,
            guild: String,
        ): TriggerAudio = TriggerAudio(
            id = TriggerAudioId(id),
            trigger = Trigger.TriggerId(trigger),
            guild = GuildId(guild),
        )
    }

    override fun id(): String = id.value
}
