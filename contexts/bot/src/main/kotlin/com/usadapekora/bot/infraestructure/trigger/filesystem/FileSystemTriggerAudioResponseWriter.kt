package com.usadapekora.bot.infraestructure.trigger.filesystem

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.audio.*
import com.usadapekora.shared.domain.file.DomainFileWriter

class FileSystemTriggerAudioResponseWriter(private val writer: DomainFileWriter) : TriggerAudioResponseWriter {
    override fun write(responseAudio: TriggerAudioResponse, content: TriggerAudioResponseContent): Either<TriggerAudioResponseException.FailedToWrite, Unit> {
        if (content !is TriggerAudioResponseFileContent) {
            return TriggerAudioResponseException.FailedToWrite("Content ${content::class} not supported by ${this::class} writer").left()
        }

        val path = TriggerAudioResponseSourceUriFactory.getFilePathFromUri(responseAudio.sourceUri.value)
            .onLeft { return TriggerAudioResponseException.FailedToWrite(it.message).left() }
            .getOrNull()!!

        writer.write(content.fileContent, path)
            .onLeft { return TriggerAudioResponseException.FailedToWrite("Unknown error writing the file").left() }

        return Unit.right()
    }
}
