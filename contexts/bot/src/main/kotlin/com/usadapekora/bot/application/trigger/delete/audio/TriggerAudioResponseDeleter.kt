package com.usadapekora.bot.application.trigger.delete.audio

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseSourceUriFactory
import com.usadapekora.shared.domain.file.DomainFileDeleter

class TriggerAudioResponseDeleter(private val repository: TriggerAudioResponseRepository, private val  fileDeleter: DomainFileDeleter) {

    fun delete(id: String): Either<TriggerAudioResponseException, Unit> {
        val audio = repository.find(TriggerAudioResponseId(id))
            .onLeft { return it.left() }
            .getOrNull()!!

        val audioFilePath = TriggerAudioResponseSourceUriFactory.getFilePathFromUri(audio.sourceUri.value)
            .onLeft { return TriggerAudioResponseException.FailedToDelete(it.message).left() }
            .getOrNull()!!

        fileDeleter.delete(audioFilePath)
            .onLeft { return TriggerAudioResponseException.FailedToDelete("Failed to delete the file of trigger audio with id ${audio.id.value}").left() }

        return repository.delete(audio).right()
    }

}
