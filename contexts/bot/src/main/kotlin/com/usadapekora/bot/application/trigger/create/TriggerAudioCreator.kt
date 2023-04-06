package com.usadapekora.bot.application.trigger.create

import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefault
import com.usadapekora.bot.domain.shared.file.DomainFileWriter
import com.usadapekora.bot.domain.trigger.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.exception.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.utils.TriggerAudioUtils
import java.io.File
import kotlin.io.path.Path

class TriggerAudioCreator(private val repository: TriggerAudioDefaultRepository, private val writer: DomainFileWriter) {

    fun create(request: TriggerAudioCreateRequest) {
        val audio = TriggerAudioDefault.fromPrimitives(
            id = request.id,
            trigger = request.triggerId,
            guild = request.guildId,
            file = "${request.id}.${File(request.fileName).extension}"
        )

        try {
            repository.find(audio.id)
            throw TriggerAudioResponseException.AlreadyExists("Trigger audio with id ${audio.id.value} already exists")
        } catch (_: TriggerAudioResponseException.NotFound) {
            val destination = Path(
                TriggerAudioUtils.audioDirPath(audio),
                audio.file.value
            ).toString()

            writer.write(request.content, destination)
            repository.save(audio)
        }
    }

}
