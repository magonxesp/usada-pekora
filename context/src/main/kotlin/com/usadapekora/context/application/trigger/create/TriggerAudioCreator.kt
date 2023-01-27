package com.usadapekora.context.application.trigger.create

import com.usadapekora.context.domain.trigger.TriggerAudio
import com.usadapekora.context.domain.trigger.TriggerAudioRepository
import com.usadapekora.context.domain.shared.file.DomainFileWriter
import com.usadapekora.context.domain.trigger.TriggerAudioException
import com.usadapekora.context.domain.trigger.utils.TriggerAudioUtils
import java.io.File
import kotlin.io.path.Path

class TriggerAudioCreator(private val repository: TriggerAudioRepository, private val writer: DomainFileWriter) {

    fun create(request: TriggerAudioCreateRequest) {
        val audio = TriggerAudio.fromPrimitives(
            id = request.id,
            trigger = request.triggerId,
            guild = request.guildId,
            file = "${request.id}.${File(request.fileName).extension}"
        )

        try {
            repository.find(audio.id)
            throw TriggerAudioException.AlreadyExists("Trigger audio with id ${audio.id.value} already exists")
        } catch (_: TriggerAudioException.NotFound) {
            val destination = Path(
                TriggerAudioUtils.audioDirPath(audio),
                audio.file.value
            ).toString()

            writer.write(request.content, destination)
            repository.save(audio)
        }
    }

}
