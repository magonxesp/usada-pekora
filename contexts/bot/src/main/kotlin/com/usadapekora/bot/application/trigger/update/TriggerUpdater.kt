package com.usadapekora.bot.application.trigger.update

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.trigger.BuiltInTriggerRepository
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.TriggerRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseProvider
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseId
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository

class TriggerUpdater(
    private val repository: TriggerRepository,
    private val textResponseRepository: TriggerTextResponseRepository,
    private val audioResponseRepository: TriggerAudioResponseRepository,
    private val builtInTriggerRepository: BuiltInTriggerRepository
) {

    private fun updateTextResponse(request: TriggerUpdateRequest, trigger: Trigger): Either<TriggerException, Unit> {
        if (request.values.responseTextId == null) {
            trigger.responseText = null
            return Unit.right()
        }

        textResponseRepository.find(TriggerTextResponseId(request.values.responseTextId))
            .onLeft { return TriggerException.MissingResponse("The new text response is missing").left() }
            .onRight { trigger.responseText = it.id }

        return Unit.right()
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

        audioResponseRepository.find(TriggerAudioResponseId(request.values.responseAudioId))
            .onLeft { return TriggerException.MissingResponse("The new audio response is missing").left() }
            .onRight {
                trigger.responseAudio = TriggerAudioResponseId(request.values.responseAudioId)
                trigger.responseAudioProvider = TriggerAudioResponseProvider.fromValue(request.values.responseAudioProvider)
            }

        return Unit.right()
    }

    fun update(request: TriggerUpdateRequest): Either<TriggerException, Unit> {
        builtInTriggerRepository.find(Trigger.TriggerId(request.id))
            .onRight { return TriggerException.UnsupportedKind("The built-in triggers are not updatable").left() }

        if (request.values.responseAudioId == null && request.values.responseTextId == null) {
            return TriggerException.MissingResponse("The trigger should have at least one response").left()
        }

        if (request.values.responseAudioId != null && request.values.responseAudioProvider == null) {
            return TriggerException.MissingAudioProvider("The trigger should have audio provider if it has audio response").left()
        }

        val trigger = repository.find(Trigger.TriggerId(request.id))
            .onLeft { it.left() }
            .getOrNull()!!

        request.values.title.takeUnless { it == null }?.let {
            trigger.title = Trigger.TriggerTitle(it)
        }

        request.values.input.takeUnless { it == null }?.let {
            trigger.input = Trigger.TriggerInput(it)
        }

        request.values.compare.takeUnless { it == null }?.let {
            trigger.compare = Trigger.TriggerCompare.fromValue(it)
        }

        updateTextResponse(request, trigger).onLeft { return it.left() }
        updateAudioResponse(request, trigger).onLeft { return it.left() }

        request.values.guildId.takeUnless { it == null }?.let {
            trigger.guildId = Guild.GuildId(it)
        }

        return repository.save(trigger).right()
    }

}
