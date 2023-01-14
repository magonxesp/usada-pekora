package com.usadapekora.context.domain.trigger

import com.usadapekora.context.domain.shared.Entity
import com.usadapekora.context.domain.shared.file.UploadedFile
import com.usadapekora.context.domain.shared.valueobject.UuidValueObject

data class TriggerAudio(
    val id: TriggerAudioId,
    val trigger: Trigger.TriggerId,
    val name: TriggerAudioName,
    val path: TriggerAudioPath
) : Entity(), UploadedFile {
    data class TriggerAudioId(override val value: String) : UuidValueObject(value)
    data class TriggerAudioName(val value: String)
    data class TriggerAudioPath(val value: String)

    override fun id(): String = id.value
    override fun name(): String = name.value
    override fun path(): String = path.value
    override fun url(): String = path.value
    override fun mimeType(): String = "audio/mpeg"

    companion object {
        fun fromPrimitives(
            id: String,
            trigger: String,
            name: String,
            path: String
        ): TriggerAudio = TriggerAudio(
            id = TriggerAudioId(id),
            trigger = Trigger.TriggerId(trigger),
            name = TriggerAudioName(name),
            path = TriggerAudioPath(path)
        )
    }
}
