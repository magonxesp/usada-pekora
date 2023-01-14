package com.usadapekora.context.domain.trigger.utils

import com.usadapekora.context.domain.guild.GuildId
import com.usadapekora.context.domain.trigger.Trigger
import com.usadapekora.context.domain.trigger.TriggerAudio
import com.usadapekora.context.storageDirPath
import kotlin.io.path.Path

object TriggerAudioUtils {
    fun audioDirPath(guildId: GuildId, triggerId: Trigger.TriggerId): String
        = Path(storageDirPath, "trigger", "audio", guildId.value, triggerId.value).toString()

    fun audioDirPath(entity: TriggerAudio): String = audioDirPath(entity.guild, entity.trigger)
}
