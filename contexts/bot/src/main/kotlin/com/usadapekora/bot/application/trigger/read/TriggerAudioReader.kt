package com.usadapekora.bot.application.trigger.read

import com.usadapekora.bot.domain.shared.file.DomainFileReader
import com.usadapekora.bot.domain.trigger.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.utils.TriggerAudioUtils
import kotlin.io.path.Path

class TriggerAudioReader(private val repository: TriggerAudioDefaultRepository, private val reader: DomainFileReader) {

    fun read(id: String): ByteArray {
        val audio = repository.find(TriggerAudioResponseId(id))
        return reader.read(Path(TriggerAudioUtils.audioDirPath(audio), audio.file.value).toString())
    }

}
