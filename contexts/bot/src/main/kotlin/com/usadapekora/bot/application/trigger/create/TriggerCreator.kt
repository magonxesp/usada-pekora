package com.usadapekora.bot.application.trigger.create

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.TriggerKind
import com.usadapekora.bot.domain.trigger.TriggerRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseId
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository

class TriggerCreator(
    private val repository: TriggerRepository,
    private val audioResponseRepository: TriggerAudioResponseRepository,
    private val textResponseRepository: TriggerTextResponseRepository
) {

    fun create(request: TriggerCreateRequest): Either<TriggerException, Unit> {
        val textResponse = request.responseTextId?.let {
            textResponseRepository.find(TriggerTextResponseId(it)).getOrNull()
        }

        val audioResponse = request.responseAudioId?.let {
            audioResponseRepository.find(TriggerAudioResponseId(it)).getOrNull()
        }

        val trigger = Either.catch {
            Trigger.fromPrimitives(
                id = request.id,
                title = request.title,
                input = request.input,
                compare = request.compare,
                kind = TriggerKind.PRIVATE.value,
                responseTextId = textResponse?.id?.value,
                responseAudioId = audioResponse?.id(),
                guildId = request.guildId
            )
        }
        .mapLeft { it as TriggerException }
        .onLeft { return it.left() }
        .getOrNull()!!

        repository.find(trigger.id).onRight {
            return TriggerException.AlreadyExists("The trigger with id ${trigger.id.value} already exists").left()
        }

        return repository.save(trigger).right()
    }

}
