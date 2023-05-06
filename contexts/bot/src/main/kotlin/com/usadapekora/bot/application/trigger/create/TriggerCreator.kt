package com.usadapekora.bot.application.trigger.create

import com.usadapekora.bot.domain.shared.tryOrNull
import com.usadapekora.bot.domain.trigger.*
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseProvider
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponse
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseId
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository

class TriggerCreator(
    private val repository: TriggerRepository,
    private val audioResponseRepository: TriggerAudioResponseRepository,
    private val textResponseRepository: TriggerTextResponseRepository
) {

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
