package com.usadapekora.bot.application.trigger.delete.audio

import com.usadapekora.bot.domain.shared.file.DomainFileDeleter
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.utils.TriggerAudioUtils
import kotlin.io.path.Path

class TriggerDefaultAudioDeleter(private val repository: TriggerAudioDefaultRepository, private val  fileDeleter: DomainFileDeleter) {

    fun delete(id: String) {
        val audio = repository.find(TriggerAudioResponseId(id))
        fileDeleter.delete(Path(TriggerAudioUtils.audioDirPath(audio), audio.file.value).toString())
        repository.delete(audio)
    }

}
