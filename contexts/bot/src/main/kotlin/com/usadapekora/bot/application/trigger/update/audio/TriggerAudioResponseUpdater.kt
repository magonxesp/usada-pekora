package com.usadapekora.bot.application.trigger.update.audio

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.audio.*
import com.usadapekora.shared.domain.file.DomainFileDeleter
import com.usadapekora.shared.domain.file.DomainFileWriter
import kotlin.Exception

class TriggerAudioResponseUpdater(
    private val repository: TriggerAudioResponseRepository,
    private val fileWriter: DomainFileWriter,
    private val fileDeleter: DomainFileDeleter
) {

    private fun updateAudioFile(request: TriggerAudioResponseFileUpdateRequest): Pair<TriggerAudioResponse.TriggerAudioResponseKind, TriggerAudioResponse.TriggerAudioResponseSource> {
        val destination = triggerAudioFilePath(
            guildId = Guild.GuildId(request.values.guildId),
            triggerId = Trigger.TriggerId(request.values.triggerId),
            fileName = request.values.fileName
        )

        fileWriter.write(request.values.fileContent, destination).onLeft {
            throw Exception("Unknown error writing the new audio file")
        }

        return Pair(
            TriggerAudioResponse.TriggerAudioResponseKind.FILE,
            TriggerAudioResponse.TriggerAudioResponseSource(destination)
        )
    }

    private fun ensureFileIsDeleted(responseAudio: TriggerAudioResponse) {
        if (responseAudio.kind == TriggerAudioResponse.TriggerAudioResponseKind.FILE) {
            fileDeleter.delete(responseAudio.source.value).onLeft {
                throw Exception("Unknown error deleting the old audio file")
            }
        }
    }

    fun update(request: TriggerAudioResponseUpdateRequest): Either<TriggerAudioResponseException, Unit> {
        val responseAudio = repository.find(TriggerAudioResponse.TriggerAudioResponseId(request.id))
            .onLeft { return TriggerAudioResponseException.NotFound("Trigger audio response with id ${request.id} not found").left() }
            .getOrNull()!!

        try {
            val (kind, source) = when (request) {
                is TriggerAudioResponseFileUpdateRequest -> updateAudioFile(request)
                is TriggerAudioResponseUrlUpdateRequest -> Pair(
                    TriggerAudioResponse.TriggerAudioResponseKind.valueOf(request.values.type.uppercase()),
                    TriggerAudioResponse.TriggerAudioResponseSource(request.values.source)
                )
                else -> return TriggerAudioResponseException.UnsupportedAudioSource("The request given is unsupported").left()
            }

            ensureFileIsDeleted(responseAudio)

            responseAudio.kind = kind
            responseAudio.source = source

            repository.save(responseAudio)
        } catch (exception: Exception) {
            return TriggerAudioResponseException.FailedToUpdate(exception.message).left()
        }

        return Unit.right()
    }
}
