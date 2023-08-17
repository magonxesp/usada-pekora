package com.usadapekora.bot.domain.trigger.audio

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild.GuildId
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.shared.storageDirPath
import kotlin.io.path.Path

object TriggerAudioResponseSourceUriFactory {
    private const val FILE_URI_PROTOCOL = "file://"

    fun getFilePath(guildId: GuildId, triggerId: Trigger.TriggerId, fileName: String) =
        Path(storageDirPath, "trigger", "audio", guildId.value, triggerId.value, fileName).toString()

    fun getFilePathFromUri(sourceUri: String): Either<TriggerAudioResponseException.InvalidSourceUri, String> =
        if (sourceUri.startsWith(FILE_URI_PROTOCOL)) {
            sourceUri.replace(FILE_URI_PROTOCOL, "").right()
        } else {
            TriggerAudioResponseException.InvalidSourceUri("The sourceUri $sourceUri is not a valid file uri").left()
        }

    fun getFileUri(guildId: GuildId, triggerId: Trigger.TriggerId, fileName: String) =
        "$FILE_URI_PROTOCOL${getFilePath(guildId, triggerId, fileName)}"
}
