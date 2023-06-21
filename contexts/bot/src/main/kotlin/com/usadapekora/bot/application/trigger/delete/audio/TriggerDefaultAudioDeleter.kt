package com.usadapekora.bot.application.trigger.delete.audio

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseId
import com.usadapekora.shared.domain.file.DomainFileDeleter

class TriggerDefaultAudioDeleter(private val repository: TriggerAudioDefaultRepository, private val  fileDeleter: DomainFileDeleter) {

    fun delete(id: String): Either<TriggerAudioResponseException, Unit> {
        val result = repository.find(TriggerAudioResponseId(id))

        if (result.leftOrNull() != null) {
            return result.leftOrNull()!!.left()
        }

        val audio = result.getOrNull()!!

        fileDeleter.delete(audio.path)
            .takeUnless { it.isRight() }
            ?.let {
                return TriggerAudioResponseException.FailedToDelete("Failed to delete the file of trigger audio with id ${audio.id.value}").left()
            }

        return repository.delete(audio).right()
    }

}
