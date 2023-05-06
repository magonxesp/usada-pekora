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
        if (request.values.responseTextId != null) {
            val id = tryOrNull<TriggerTextResponseException.NotFound, TriggerTextResponse> {
                textResponseRepository.find(TriggerTextResponseId(request.values.responseTextId))
            }?.id ?: throw TriggerException.MissingResponse("The new text response is missing")

            trigger.responseText = id
        } else {
            trigger.responseText = null
        }
    }

    private fun updateAudioResponse(request: TriggerUpdateRequest, trigger: Trigger) {
        if (request.values.responseAudioId != null) {
            if (request.values.responseAudioProvider == null) {
                throw TriggerException.MissingAudioProvider("Missing audio provider for the new audio response")
            }

            tryOrNull<TriggerAudioResponseException.NotFound, TriggerAudioResponse> {
                audioResponseRepository.find(TriggerAudioResponseId(request.values.responseAudioId))
            } ?: throw TriggerException.MissingResponse("The new audio response is missing")

            trigger.responseAudio = TriggerAudioResponseId(request.values.responseAudioId)
            trigger.responseAudioProvider = TriggerAudioResponseProvider.fromValue(request.values.responseAudioProvider)
        } else {
            trigger.responseAudio = null
            trigger.responseAudioProvider = null
        }
    }

    fun update(request: TriggerUpdateRequest) {
        if (request.values.responseAudioId == null && request.values.responseTextId == null) {
            throw TriggerException.MissingResponse("The trigger should have at least one response")
        }

        if (request.values.responseAudioId != null && request.values.responseAudioProvider == null) {
            throw TriggerException.MissingAudioProvider("The trigger should have audio provider if it has audio response")
        }

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
