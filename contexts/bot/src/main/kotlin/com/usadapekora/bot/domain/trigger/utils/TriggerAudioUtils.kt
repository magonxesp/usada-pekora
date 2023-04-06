package com.usadapekora.bot.domain.trigger.utils

import com.usadapekora.bot.domain.guild.GuildId
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefault
import com.usadapekora.bot.storageDirPath
import kotlin.io.path.Path

object TriggerAudioUtils {
    fun audioDirPath(guildId: GuildId, triggerId: Trigger.TriggerId): String
        = Path(storageDirPath, "trigger", "audio", guildId.value, triggerId.value).toString()

    fun audioDirPath(entity: TriggerAudioDefault): String = audioDirPath(entity.guild, entity.trigger)
}
