package com.usadapekora.context.application.trigger

import com.usadapekora.context.domain.trigger.TriggerAudio
import com.usadapekora.context.domain.trigger.TriggerAudioRepository
import com.usadapekora.context.domain.shared.file.UploadedFileWriter

class TriggerAudioCreator(private val repository: TriggerAudioRepository, private val writer: UploadedFileWriter) {

    fun create(request: TriggerAudioCreateRequest) {
        val audio = TriggerAudio.fromPrimitives(
            id = request.id,
            trigger = request.triggerId,
            name = request.fileName,
            path = "trigger/audio/${request.id}.${request.fileName.substringAfterLast('.')}"
        )

        writer.write(request.fileContent, audio.path.value)
        repository.save(audio)
    }

}
