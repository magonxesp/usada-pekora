package com.usadapekora.bot.application.trigger.update

import arrow.core.Either
import arrow.core.left
import arrow.core.right
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

    private fun updateTextResponse(request: TriggerUpdateRequest, trigger: Trigger): Either<TriggerException, Unit> {
        if (request.values.responseTextId == null) {
            trigger.responseText = null
            return Unit.right()
        }

        return textResponseRepository.find(TriggerTextResponseId(request.values.responseTextId)).let {
            if (it.isLeft()) return TriggerException.MissingResponse("The new text response is missing").left()
            trigger.responseText = it.getOrNull()!!.id
        }.right()
    }

    private fun updateAudioResponse(request: TriggerUpdateRequest, trigger: Trigger): Either<TriggerException, Unit> {
        if (request.values.responseAudioId == null) {
            trigger.responseAudio = null
            trigger.responseAudioProvider = null
            return Unit.right()
        }

        if (request.values.responseAudioProvider == null) {
            return TriggerException.MissingAudioProvider("Missing audio provider for the new audio response").left()
        }

        return audioResponseRepository.find(TriggerAudioResponseId(request.values.responseAudioId)).let {
            if (it.isLeft()) return TriggerException.MissingResponse("The new audio response is missing").left()
            trigger.responseAudio = TriggerAudioResponseId(request.values.responseAudioId)
            trigger.responseAudioProvider = TriggerAudioResponseProvider.fromValue(request.values.responseAudioProvider)
        }.right()
    }

    fun update(request: TriggerUpdateRequest): Either<TriggerException, Unit> {
        if (request.values.responseAudioId == null && request.values.responseTextId == null) {
            return TriggerException.MissingResponse("The trigger should have at least one response").left()
        }

        if (request.values.responseAudioId != null && request.values.responseAudioProvider == null) {
            return TriggerException.MissingAudioProvider("The trigger should have audio provider if it has audio response").left()
        }

        val result = repository.find(Trigger.TriggerId(request.id))

        if (result.isLeft()) {
            return result.leftOrNull()!!.left()
        }

        val trigger = result.getOrNull()!!

        request.values.title.takeUnless { it == null }?.let {
            trigger.title = Trigger.TriggerTitle(it)
        }

        request.values.input.takeUnless { it == null }?.let {
            trigger.input = Trigger.TriggerInput(it)
        }

        request.values.compare.takeUnless { it == null }?.let {
            trigger.compare = Trigger.TriggerCompare.fromValue(it)
        }

        updateTextResponse(request, trigger).let { if (it.isLeft()) return it.leftOrNull()!!.left() }
        updateAudioResponse(request, trigger).let { if (it.isLeft()) return it.leftOrNull()!!.left() }

        request.values.discordGuildId.takeUnless { it == null }?.let {
            trigger.discordGuildId = Trigger.TriggerDiscordGuildId(it)
        }

        return repository.save(trigger).right()
    }

}
