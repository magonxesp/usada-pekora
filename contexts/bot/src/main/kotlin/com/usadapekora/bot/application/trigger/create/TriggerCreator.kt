package com.usadapekora.bot.application.trigger.create

import com.usadapekora.bot.domain.shared.tryOrNull
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
                TriggerAudioResponseProvider.fromValue(request.responseAudioProvider)
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
        val textResponse = request.responseTextId?.let {
            tryOrNull<TriggerTextResponseException.NotFound, TriggerTextResponse> {
                textResponseRepository.find(TriggerTextResponseId(it))
            }
        }

        val audioResponse = request.responseAudioId.takeIf {it != null && request.responseAudioProvider != null }?.let {
            tryOrNull<TriggerAudioResponseException.NotFound, TriggerAudioResponse> {
                audioResponseRepository.find(
                    TriggerAudioResponseId(it),
                    TriggerAudioResponseProvider.fromValue(request.responseAudioProvider!!)
                )
            }
        }

        val trigger = Trigger.fromPrimitives(
            id = request.id,
            title = request.title,
            input = request.input,
            compare = request.compare,
            responseTextId = textResponse?.id?.value,
            responseAudioId = audioResponse?.id(),
            responseAudioProvider = request.responseAudioProvider,
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
