package com.usadapekora.bot.domain.trigger.audio

import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.shared.storageDirPath
import kotlin.io.path.Path

fun triggerAudioFilePath(guildId: Guild.GuildId, triggerId: Trigger.TriggerId, fileName: String) =
    Path(storageDirPath, guildId.value, triggerId.value, fileName).toString()
