package com.usadapekora.bot.domain.trigger.audio

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.trigger.Trigger

class TriggerAudioResponseFileSourceCreator : TriggerAudioResponseCreator {
    override fun create(
        id: String,
        triggerId: String,
        guildId: String,
        content: TriggerAudioResponseContent
    ): Either<TriggerAudioResponseException.FailedToCreate, TriggerAudioResponse> {
        if (content !is TriggerAudioResponseFileContent) {
            return TriggerAudioResponseException.FailedToCreate("The trigger audio response content is not of file type").left()
        }

        return TriggerAudioResponse.fromPrimitives(
            id = id,
            source = TriggerAudioResponse.TriggerAudioResponseSource.FILE.name,
            sourceUri = TriggerAudioResponseSourceUriFactory.getFileUri(
                guildId = Guild.GuildId(guildId),
                triggerId = Trigger.TriggerId(triggerId),
                fileName = content.fileName
            )
        ).right()
    }
}
