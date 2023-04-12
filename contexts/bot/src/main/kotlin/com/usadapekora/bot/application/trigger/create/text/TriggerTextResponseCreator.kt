package com.usadapekora.bot.application.trigger.create.text

import com.usadapekora.bot.domain.trigger.text.TriggerTextResponse
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository

class TriggerTextResponseCreator(private val repository: TriggerTextResponseRepository) {

    fun create(request: TriggerTextResponseCreateRequest) {
        val textResponse = TriggerTextResponse.fromPrimitives(
            id = request.id,
            content = request.content,
            type = request.type
        )

        try {
            repository.find(textResponse.id)
            throw TriggerTextResponseException.AlreadyExists("The response text with id ${request.id} already exists")
        } catch (_: TriggerTextResponseException.NotFound) {
            repository.save(textResponse)
        }
    }

}
