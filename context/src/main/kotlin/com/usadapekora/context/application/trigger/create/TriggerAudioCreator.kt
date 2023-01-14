package com.usadapekora.context.application.trigger.create

import com.usadapekora.context.domain.trigger.TriggerAudio
import com.usadapekora.context.domain.trigger.TriggerAudioRepository
import com.usadapekora.context.domain.shared.file.UploadedFileWriter

class TriggerAudioCreator(private val repository: TriggerAudioRepository, private val writer: UploadedFileWriter) {

    fun create(request: TriggerAudioCreateRequest) {
        val audio = TriggerAudio.fromPrimitives(
            id = request.id,
            trigger = request.triggerId,
            guild = request.guildId,
            name = request.fileName
        )

        writer.write(request.fileContent, audio.path())
        repository.save(audio)
    }

}
