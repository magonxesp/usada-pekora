package com.usadapekora.context.domain.trigger

import com.usadapekora.context.domain.guild.GuildId
import com.usadapekora.context.domain.shared.Entity
import com.usadapekora.context.domain.shared.file.UploadedFile
import com.usadapekora.context.domain.shared.valueobject.UuidValueObject
import com.usadapekora.context.storageDirPath
import kotlin.io.path.Path
import kotlin.io.path.pathString

data class TriggerAudio(
    val id: TriggerAudioId,
    val trigger: Trigger.TriggerId,
    val guild: GuildId,
    val name: TriggerAudioName,
) : Entity(), UploadedFile {
    data class TriggerAudioId(override val value: String) : UuidValueObject(value)
    data class TriggerAudioName(val value: String)

    override fun id(): String = id.value
    override fun name(): String = name.value
    override fun path(): String
        = Path(storageDirPath, "trigger", "audio", guild.value, trigger.value, name.value).pathString
    override fun url(): String = ""
    override fun mimeType(): String = "audio/mpeg"

    companion object {
        fun fromPrimitives(
            id: String,
            trigger: String,
            guild: String,
            name: String,
        ): TriggerAudio = TriggerAudio(
            id = TriggerAudioId(id),
            trigger = Trigger.TriggerId(trigger),
            guild = GuildId(guild),
            name = TriggerAudioName(name)
        )
    }
}
