package com.usadapekora.bot.application.trigger.delete

import com.usadapekora.bot.domain.shared.file.DomainFileDeleter
import com.usadapekora.bot.domain.trigger.TriggerAudio
import com.usadapekora.bot.domain.trigger.TriggerAudioRepository
import com.usadapekora.bot.domain.trigger.utils.TriggerAudioUtils
import kotlin.io.path.Path

class TriggerAudioDeleter(private val repository: TriggerAudioRepository, private val  fileDeleter: DomainFileDeleter) {

    fun delete(id: String) {
        val audio = repository.find(TriggerAudio.TriggerAudioId(id))
        fileDeleter.delete(Path(TriggerAudioUtils.audioDirPath(audio), audio.file.value).toString())
        repository.delete(audio)
    }

}
