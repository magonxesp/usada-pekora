package com.usadapekora.bot.application.trigger.update

import com.usadapekora.bot.domain.shared.tryOrNull
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.TriggerRepository
import com.usadapekora.bot.domain.trigger.audio.*
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponse
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseId
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository

class TriggerUpdater(
    private val repository: TriggerRepository,
    private val textResponseRepository: TriggerTextResponseRepository,
    private val audioResponseRepository: TriggerAudioResponseRepository
) {

    private fun updateTextResponse(request: TriggerUpdateRequest, trigger: Trigger) {
        request.values.responseTextId.takeUnless { it == null }?.let {
            val id = tryOrNull<TriggerTextResponseException.NotFound, TriggerTextResponse> {
                textResponseRepository.find(TriggerTextResponseId(it))
            }?.id ?: throw TriggerException.MissingResponse("The new text response is missing")

            trigger.responseText = id
        }
    }

    private fun updateAudioResponse(request: TriggerUpdateRequest, trigger: Trigger) {
        request.values.responseAudioId.takeUnless { it == null }?.let {
            if (request.values.responseAudioProvider == null) {
                throw TriggerException.MissingAudioProvider("Missing audio provider for the new audio response")
            }

            tryOrNull<TriggerAudioResponseException.NotFound, TriggerAudioResponse> {
                audioResponseRepository.find(TriggerAudioResponseId(it))
            } ?: throw TriggerException.MissingResponse("The new audio response is missing")

            trigger.responseAudio = TriggerAudioResponseId(it)
            trigger.responseAudioProvider = TriggerAudioResponseProvider.fromValue(request.values.responseAudioProvider)
        }
    }

    fun update(request: TriggerUpdateRequest) {
        val trigger = repository.find(Trigger.TriggerId(request.id))

        request.values.title.takeUnless { it == null }?.let {
            trigger.title = Trigger.TriggerTitle(it)
        }

        request.values.input.takeUnless { it == null }?.let {
            trigger.input = Trigger.TriggerInput(it)
        }

        request.values.compare.takeUnless { it == null }?.let {
            trigger.compare = Trigger.TriggerCompare.fromValue(it)
        }

        updateTextResponse(request, trigger)
        updateAudioResponse(request, trigger)

        request.values.discordGuildId.takeUnless { it == null }?.let {
            trigger.discordGuildId = Trigger.TriggerDiscordGuildId(it)
        }

        repository.save(trigger)
    }

}
