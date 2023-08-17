package com.usadapekora.bot.application.trigger.update.audio

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild.GuildId
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.audio.*
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseSourceUriFactory
import com.usadapekora.shared.domain.file.DomainFileDeleter
import com.usadapekora.shared.domain.file.DomainFileWriter

class TriggerAudioResponseUpdater(
    private val repository: TriggerAudioResponseRepository,
    private val fileWriter: DomainFileWriter,
    private val fileDeleter: DomainFileDeleter
) {
    fun update(request: TriggerAudioResponseUpdateRequest): Either<TriggerAudioResponseException, Unit> {
        val audioResponse = repository.find(TriggerAudioResponseId(request.id))
            .onLeft { return it.left() }
            .getOrNull()!!

        val oldFileUri = audioResponse.sourceUri.value
        val newFileUri = TriggerAudioResponseSourceUriFactory.getFileUri(
            guildId = GuildId(request.values.guildId),
            triggerId = Trigger.TriggerId(request.values.triggerId),
            fileName = request.values.fileName
        )

        if (oldFileUri != newFileUri) {
            val destination = TriggerAudioResponseSourceUriFactory.getFilePath(
                guildId = GuildId(request.values.guildId),
                triggerId = Trigger.TriggerId(request.values.triggerId),
                fileName = request.values.fileName
            )

            fileWriter.write(request.values.content, destination).onLeft {
                return TriggerAudioResponseException.FailedToUpdate("Failed to write audio file of trigger with id ${audioResponse.id.value}").left()
            }

            val oldFilePath = TriggerAudioResponseSourceUriFactory.getFilePathFromUri(oldFileUri)
                .onLeft { return TriggerAudioResponseException.FailedToUpdate(it.message).left() }
                .getOrNull()!!

            fileDeleter.delete(oldFilePath).onLeft {
                return TriggerAudioResponseException.FailedToDelete("Failed to delete audio file of trigger with id ${audioResponse.id.value}").left()
            }
        }

        return repository.save(audioResponse).right()
    }
}
