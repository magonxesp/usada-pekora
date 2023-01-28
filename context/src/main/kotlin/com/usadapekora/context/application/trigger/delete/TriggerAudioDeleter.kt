package com.usadapekora.context.application.trigger.delete

import com.usadapekora.context.domain.shared.file.DomainFileDeleter
import com.usadapekora.context.domain.trigger.TriggerAudio
import com.usadapekora.context.domain.trigger.TriggerAudioRepository
import com.usadapekora.context.domain.trigger.utils.TriggerAudioUtils
import kotlin.io.path.Path

class TriggerAudioDeleter(private val repository: TriggerAudioRepository, private val  fileDeleter: DomainFileDeleter) {

    fun delete(id: String) {
        val audio = repository.find(TriggerAudio.TriggerAudioId(id))
        fileDeleter.delete(Path(TriggerAudioUtils.audioDirPath(audio), audio.file.value).toString())
        repository.delete(audio)
    }

}
