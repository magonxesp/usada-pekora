package com.usadapekora.bot.application.trigger.read

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.shared.domain.file.DomainFileReader
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.utils.TriggerAudioUtils
import kotlin.io.path.Path

class TriggerDefaultAudioReader(private val repository: TriggerAudioDefaultRepository, private val reader: DomainFileReader) {

    fun read(id: String): Either<TriggerAudioResponseException, ByteArray> {
        val result = repository.find(TriggerAudioResponseId(id))

        if (result.isLeft()) {
            return result.leftOrNull()!!.left()
        }

        val audio = result.getOrNull()!!
        val content = reader.read(audio.path)

        if (content.isLeft()) {
            return TriggerAudioResponseException.FailedToRead("Failed read the audio of trigger audio with id ${audio.id.value}").left()
        }

        return content.getOrNull()!!.right()
    }

}
