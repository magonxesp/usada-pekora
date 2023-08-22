package com.usadapekora.bot.application.trigger.create.audio

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.audio.*
import com.usadapekora.shared.domain.file.DomainFileWriter

class TriggerAudioResponseCreator(
    private val repository: TriggerAudioResponseRepository,
    private val domainFileWriter: DomainFileWriter
) {

    private fun writeAudioFile(request: TriggerAudioResponseFileCreateRequest): Pair<TriggerAudioResponse.TriggerAudioResponseKind, TriggerAudioResponse.TriggerAudioResponseSource> {
        val destinationPath = triggerAudioFilePath(
            guildId = Guild.GuildId(request.guildId),
            triggerId = Trigger.TriggerId(request.triggerId),
            fileName = request.fileName
        )

        domainFileWriter.write(request.fileContent, destinationPath)
            .onLeft { throw TriggerAudioResponseException.FailedToWrite("Failed to write audio file") }

        return Pair(
            TriggerAudioResponse.TriggerAudioResponseKind.FILE,
            TriggerAudioResponse.TriggerAudioResponseSource(destinationPath)
        )
    }

    fun create(request: TriggerAudioResponseCreateRequest): Either<TriggerAudioResponseException, Unit> {
        repository.find(TriggerAudioResponse.TriggerAudioResponseId(request.id))
            .onRight { return TriggerAudioResponseException.AlreadyExists("The trigger audio response with id ${request.id} already exists!").left() }

        try {
            val (kind, source) = when (request) {
                is TriggerAudioResponseFileCreateRequest -> writeAudioFile(request)
                is TriggerAudioResponseUrlCreateRequest -> Pair(
                    TriggerAudioResponse.TriggerAudioResponseKind.valueOf(request.type.uppercase()),
                    TriggerAudioResponse.TriggerAudioResponseSource(request.source)
                )
                else -> return TriggerAudioResponseException.UnsupportedAudioSource("The request given is unsupported").left()
            }

            val responseAudio = TriggerAudioResponse.fromPrimitives(
                id = request.id,
                kind = kind.name,
                source = source.value
            )

            repository.save(responseAudio)
        } catch (exception: Exception) {
            return TriggerAudioResponseException.FailedToCreate(exception.message).left()
        }

        return Unit.right()
    }

}
