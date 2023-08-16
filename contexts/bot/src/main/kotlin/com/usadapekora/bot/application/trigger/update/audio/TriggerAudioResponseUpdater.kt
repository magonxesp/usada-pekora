package com.usadapekora.bot.application.trigger.update.audio

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild.GuildId
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.audio.*
import com.usadapekora.shared.domain.file.DomainFileDeleter
import com.usadapekora.shared.domain.file.DomainFileWriter

class TriggerAudioResponseUpdater(
    private val repository: TriggerAudioResponseRepository,
    private val fileWriter: DomainFileWriter,
    private val fileDeleter: DomainFileDeleter
) {

    fun update(request: TriggerAudioResponseUpdateRequest): Either<TriggerAudioResponseException, Unit> {
        val result = repository.find(TriggerAudioResponseId(request.id))

        if (result.isLeft()) {
            return result.leftOrNull()!!.left()
        }

        val audioResponse = result.getOrNull()!!
        val oldFilePath = audioResponse.path

        request.values.triggerId.takeUnless { it == null }?.let {
            audioResponse.trigger = Trigger.TriggerId(it)
        }

        request.values.guildId.takeUnless { it == null }?.let {
            audioResponse.guild = GuildId(it)
        }

        request.values.fileName.takeUnless { it == null }?.let {
            audioResponse.sourceUri = TriggerAudioResponse.TriggerAudioResponseSourceUri(it)
        }

        request.values.content.takeUnless { it == null }?.let {
            fileWriter.write(it, audioResponse.path).leftOrNull()?.let {
                return TriggerAudioResponseException.FailedToUpdate("Failed to write audio file of trigger with id ${audioResponse.id.value}").left()
            }
        }

        if (oldFilePath != audioResponse.path) {
            fileDeleter.delete(oldFilePath).leftOrNull()?.let {
                return TriggerAudioResponseException.FailedToDelete("Failed to delete audio file of trigger with id ${audioResponse.id.value}").left()
            }
        }

        return repository.save(audioResponse).right()
    }

}
