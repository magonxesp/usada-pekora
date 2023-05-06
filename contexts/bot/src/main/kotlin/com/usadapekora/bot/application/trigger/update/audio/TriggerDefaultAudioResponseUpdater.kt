package com.usadapekora.bot.application.trigger.update.audio

import com.usadapekora.bot.domain.guild.GuildId
import com.usadapekora.bot.domain.shared.file.DomainFileDeleter
import com.usadapekora.bot.domain.shared.file.DomainFileWriter
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.audio.TriggerDefaultAudioResponse

class TriggerDefaultAudioResponseUpdater(
    private val repository: TriggerAudioDefaultRepository,
    private val fileWriter: DomainFileWriter,
    private val fileDeleter: DomainFileDeleter
) {

    fun update(request: TriggerDefaultAudioResponseUpdateRequest) {
        val audioResponse = repository.find(TriggerAudioResponseId(request.id))
        val oldFilePath = audioResponse.path

        request.values.triggerId.takeUnless { it == null }?.let {
            audioResponse.trigger = Trigger.TriggerId(it)
        }

        request.values.guildId.takeUnless { it == null }?.let {
            audioResponse.guild = GuildId(it)
        }

        request.values.fileName.takeUnless { it == null }?.let {
            audioResponse.file = TriggerDefaultAudioResponse.TriggerAudioFile(it)
        }

        request.values.content.takeUnless { it == null }?.let {
            fileWriter.write(it, audioResponse.path)
        }

        if (oldFilePath != audioResponse.path) {
            fileDeleter.delete(oldFilePath)
        }

        repository.save(audioResponse)
    }

}
