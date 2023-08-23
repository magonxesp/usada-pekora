package com.usadapekora.bot.domain.trigger.audio

import com.usadapekora.shared.domain.Entity
import com.usadapekora.shared.domain.valueobject.UuidValueObject

data class TriggerAudioResponse(
    val id: TriggerAudioResponseId,
    var kind: TriggerAudioResponseKind,
    var source: TriggerAudioResponseSource
) : Entity() {
    enum class TriggerAudioResponseKind {
        FILE, RESOURCE
    }
    data class TriggerAudioResponseId(override val value: String) : UuidValueObject(value)
    data class TriggerAudioResponseSource(val value: String)

    companion object {
        fun fromPrimitives(
            id: String,
            kind: String,
            source: String
        ): TriggerAudioResponse = TriggerAudioResponse(
            id = TriggerAudioResponseId(id),
            kind = TriggerAudioResponseKind.valueOf(kind.uppercase()),
            source = TriggerAudioResponseSource(source)
        )
    }

    override fun id(): String = id.value
}
