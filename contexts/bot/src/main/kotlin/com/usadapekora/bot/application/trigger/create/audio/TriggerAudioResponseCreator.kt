package com.usadapekora.bot.application.trigger.create.audio

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.audio.*

class TriggerAudioResponseCreator(
    private val creatorFactory: TriggerAudioResponseCreatorFactory,
    private val writerFactory: TriggerAudioResponseWriterFactory,
    private val repository: TriggerAudioResponseRepository
) {

    fun create(request: TriggerAudioResponseCreateRequest): Either<TriggerAudioResponseException, Unit> {
        repository.find(TriggerAudioResponseId(request.id)).onRight {
            return TriggerAudioResponseException.AlreadyExists("Trigger audio with id ${request.id} already exists").left()
        }

        val creator = creatorFactory.getInstance(request.content)
            .onLeft { return it.left() }
            .getOrNull()!!

        val writer = writerFactory.getInstance(request.content)
            .onLeft { return it.left() }
            .getOrNull()!!

        val responseAudio = creator.create(request.id, request.triggerId, request.guildId, request.content)
            .onLeft { return it.left() }
            .getOrNull()!!

        writer.write(
            responseAudio = responseAudio,
            content = request.content,
        ).onLeft {
            return TriggerAudioResponseException.FailedToSave("Failed writing the audio file of the trigger audio with id ${request.id}").left()
        }

        return repository.save(responseAudio).right()
    }

}
