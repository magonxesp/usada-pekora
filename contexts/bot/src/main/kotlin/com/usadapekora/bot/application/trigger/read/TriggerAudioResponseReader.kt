package com.usadapekora.bot.application.trigger.read

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse.TriggerAudioResponseId
import com.usadapekora.shared.domain.file.DomainFileReader

class TriggerAudioResponseReader(private val repository: TriggerAudioResponseRepository, private val reader: DomainFileReader) {

    fun read(id: String): Either<TriggerAudioResponseException, ByteArray> {
        val audio = repository.find(TriggerAudioResponseId(id))
            .onLeft { return it.left() }
            .getOrNull()!!

        val content = when (audio.kind) {
            TriggerAudioResponse.TriggerAudioResponseKind.FILE -> reader.read(audio.source.value)
                .onLeft { return TriggerAudioResponseException.FailedToRead("Unknown error reading trigger response audio file").left() }
                .getOrNull()!!
            TriggerAudioResponse.TriggerAudioResponseKind.RESOURCE -> this::class.java.getResource(audio.source.value)?.readBytes()
                ?: return TriggerAudioResponseException.FailedToRead("Unknown error reading trigger response audio file from resource").left()
            // TODO: uncomment if necessary
            // else -> return TriggerAudioResponseException.UnsupportedAudioSource("The trigger response audio kind ${audio.kind.name} is not readable").left()
        }

        return content.right()
    }

}
