package com.usadapekora.bot.application.trigger.find.text

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseId
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository

class TriggerTextResponseFinder(private val repository: TriggerTextResponseRepository) {

    fun find(id: String): Either<TriggerTextResponseException, TriggerTextResponseFindResponse> {
        val result = repository.find(TriggerTextResponseId(id))

        if (result.isLeft()) {
            return result.leftOrNull()!!.left()
        }

        val textResponse = result.getOrNull()!!
        return TriggerTextResponseFindResponse(
            id = textResponse.id.value,
            content = textResponse.content.value,
            type = textResponse.type.value
        ).right()
    }

}
