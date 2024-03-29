package com.usadapekora.bot.application.trigger.delete.audio

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse.TriggerAudioResponseId
import com.usadapekora.shared.domain.file.DomainFileDeleter

class TriggerAudioResponseDeleter(private val repository: TriggerAudioResponseRepository, private val  fileDeleter: DomainFileDeleter) {

    fun delete(id: String): Either<TriggerAudioResponseException, Unit> {
        val audio = repository.find(TriggerAudioResponseId(id))
            .onLeft { return it.left() }
            .getOrNull()!!

        if (audio.kind == TriggerAudioResponse.TriggerAudioResponseKind.FILE) {
            fileDeleter.delete(audio.source.value).onLeft {
                return TriggerAudioResponseException.FailedToDelete("Failed to delete the file of the trigger audio response").left()
            }
        }

        return repository.delete(audio).right()
    }

}
