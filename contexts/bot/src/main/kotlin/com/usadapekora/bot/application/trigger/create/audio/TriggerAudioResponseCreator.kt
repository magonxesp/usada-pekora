package com.usadapekora.bot.application.trigger.create.audio

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.shared.domain.file.DomainFileWriter

class TriggerAudioResponseCreator(private val repository: TriggerAudioResponseRepository, private val writer: DomainFileWriter) {

    fun create(request: TriggerAudioResponseCreateRequest): Either<TriggerAudioResponseException, Unit> {
        val audio = TriggerAudioResponse.fromPrimitives(
            id = request.id,
            trigger = request.triggerId,
            guild = request.guildId,
            sourceUri = request.fileName
        )

        val existing = repository.find(audio.id)

        if (existing.getOrNull() != null) {
            return TriggerAudioResponseException.AlreadyExists("Trigger audio with id ${audio.id.value} already exists").left()
        }

        writer.write(request.content, audio.path).leftOrNull()?.let {
            return TriggerAudioResponseException.FailedToSave("Failed writing the audio file of the trigger audio with id ${audio.id.value}").left()
        }

        return repository.save(audio).right()
    }

}