package com.usadapekora.bot.application.trigger.create

import com.usadapekora.bot.domain.trigger.*
import com.usadapekora.bot.domain.trigger.exception.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.exception.TriggerException
import com.usadapekora.bot.domain.trigger.exception.TriggerTextResponseException

class TriggerCreator(
    private val repository: TriggerRepository,
    private val audioResponseRepository: TriggerAudioResponseRepository,
    private val textResponseRepository: TriggerTextResponseRepository
) {

    private fun findAudioResponse(request: TriggerCreateRequest): TriggerAudioResponse? {
        if (request.responseAudioId == null || request.responseAudioProvider == null) {
            return null
        }

        return try {
            audioResponseRepository.find(
                TriggerAudioResponseId(request.responseAudioId),
                TriggerAudioProvider.fromValue(request.responseAudioProvider)
            )
        } catch (_: TriggerAudioResponseException.NotFound) {
            null
        }
    }

    private fun findTextResponse(request: TriggerCreateRequest): TriggerTextResponse? {
        if (request.responseTextId == null) {
            return null
        }

        return try {
            textResponseRepository.find(TriggerTextResponseId(request.responseTextId))
        } catch (_: TriggerTextResponseException.NotFound) {
            null
        }
    }

    fun create(request: TriggerCreateRequest) {
        val responseAudio = findAudioResponse(request)
        val responseText = findTextResponse(request)

        if (responseAudio == null && responseText == null) {
            throw TriggerException.MissingResponse("The trigger should have at least one response")
        }

        val trigger = Trigger.fromPrimitives(
            id = request.id,
            title = request.title,
            input = request.input,
            compare = request.compare,
            responseText = responseText,
            responseAudio = responseAudio,
            discordGuildId = request.discordGuildId,
        )

        try {
            repository.find(trigger.id)
            throw TriggerException.AlreadyExists("The trigger with id ${trigger.id.value} already exists")
        } catch (_: TriggerException.NotFound) {
            repository.save(trigger)
        }
    }

}
