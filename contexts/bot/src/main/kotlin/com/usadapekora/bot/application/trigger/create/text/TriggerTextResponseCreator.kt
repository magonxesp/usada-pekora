package com.usadapekora.bot.application.trigger.create.text

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponse
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository

class TriggerTextResponseCreator(private val repository: TriggerTextResponseRepository) {

    fun create(request: TriggerTextResponseCreateRequest): Either<TriggerTextResponseException, Unit> {
        val textResponse = TriggerTextResponse.fromPrimitives(
            id = request.id,
            content = request.content,
            type = request.type
        )

        val existing = repository.find(textResponse.id)

        if (existing.getOrNull() != null) {
            return TriggerTextResponseException.AlreadyExists("The response text with id ${request.id} already exists").left()
        }

        return repository.save(textResponse).right()
    }

}
