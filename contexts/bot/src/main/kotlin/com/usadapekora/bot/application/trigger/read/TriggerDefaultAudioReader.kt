package com.usadapekora.bot.application.trigger.read

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseSourceUriFactory
import com.usadapekora.shared.domain.file.DomainFileReader

class TriggerDefaultAudioReader(private val repository: TriggerAudioResponseRepository, private val reader: DomainFileReader) {

    fun read(id: String): Either<TriggerAudioResponseException, ByteArray> {
        val audio = repository.find(TriggerAudioResponseId(id))
            .onLeft { return it.left() }
            .getOrNull()!!

        val filePath = TriggerAudioResponseSourceUriFactory.getFilePathFromUri(audio.sourceUri.value)
            .onLeft { return TriggerAudioResponseException.FailedToRead(it.message).left() }
            .getOrNull()!!

        val content = reader.read(filePath)
            .onLeft { return TriggerAudioResponseException.FailedToRead("Failed read the audio of trigger audio with id ${audio.id.value}").left() }
            .getOrNull()!!

        return content.right()
    }

}
