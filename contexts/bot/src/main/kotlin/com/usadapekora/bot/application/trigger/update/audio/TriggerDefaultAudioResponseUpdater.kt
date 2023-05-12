package com.usadapekora.bot.application.trigger.update.audio

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.GuildId
import com.usadapekora.bot.domain.shared.file.DomainFileDeleter
import com.usadapekora.bot.domain.shared.file.DomainFileWriter
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.audio.TriggerDefaultAudioResponse

class TriggerDefaultAudioResponseUpdater(
    private val repository: TriggerAudioDefaultRepository,
    private val fileWriter: DomainFileWriter,
    private val fileDeleter: DomainFileDeleter
) {

    fun update(request: TriggerDefaultAudioResponseUpdateRequest): Either<TriggerAudioResponseException, Unit> {
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
            audioResponse.file = TriggerDefaultAudioResponse.TriggerAudioFile(it)
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
