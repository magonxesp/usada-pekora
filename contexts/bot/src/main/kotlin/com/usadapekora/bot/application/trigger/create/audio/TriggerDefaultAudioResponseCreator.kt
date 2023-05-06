package com.usadapekora.bot.application.trigger.create.audio

import com.usadapekora.bot.domain.trigger.audio.TriggerDefaultAudioResponse
import com.usadapekora.bot.domain.shared.file.DomainFileWriter
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.utils.TriggerAudioUtils
import java.io.File
import kotlin.io.path.Path

class TriggerDefaultAudioResponseCreator(private val repository: TriggerAudioDefaultRepository, private val writer: DomainFileWriter) {

    fun create(request: TriggerDefaultAudioResponseCreateRequest) {
        val audio = TriggerDefaultAudioResponse.fromPrimitives(
            id = request.id,
            trigger = request.triggerId,
            guild = request.guildId,
            file = request.fileName
        )

        try {
            repository.find(audio.id)
            throw TriggerAudioResponseException.AlreadyExists("Trigger audio with id ${audio.id.value} already exists")
        } catch (_: TriggerAudioResponseException.NotFound) {
            writer.write(request.content, audio.path)
            repository.save(audio)
        }
    }

}
