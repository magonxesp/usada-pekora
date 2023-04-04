package com.usadapekora.bot.application.trigger.read

import com.usadapekora.bot.domain.shared.file.DomainFileReader
import com.usadapekora.bot.domain.trigger.TriggerAudio
import com.usadapekora.bot.domain.trigger.TriggerAudioRepository
import com.usadapekora.bot.domain.trigger.utils.TriggerAudioUtils
import kotlin.io.path.Path

class TriggerAudioReader(private val repository: TriggerAudioRepository, private val reader: DomainFileReader) {

    fun read(id: String): ByteArray {
        val audio = repository.find(TriggerAudio.TriggerAudioId(id))
        return reader.read(Path(TriggerAudioUtils.audioDirPath(audio), audio.file.value).toString())
    }

}
