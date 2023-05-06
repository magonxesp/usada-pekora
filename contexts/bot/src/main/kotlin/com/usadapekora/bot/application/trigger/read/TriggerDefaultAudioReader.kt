package com.usadapekora.bot.application.trigger.read

import com.usadapekora.bot.domain.shared.file.DomainFileReader
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.utils.TriggerAudioUtils
import kotlin.io.path.Path

class TriggerDefaultAudioReader(private val repository: TriggerAudioDefaultRepository, private val reader: DomainFileReader) {

    fun read(id: String): ByteArray {
        val audio = repository.find(TriggerAudioResponseId(id))
        return reader.read(audio.path)
    }

}
