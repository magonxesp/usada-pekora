package com.usadapekora.bot.application.trigger.update.text

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.text.*

class TriggerTextResponseUpdater(private val repository: TriggerTextResponseRepository) {

    fun update(request: TriggerTextResponseUpdateRequest): Either<TriggerTextResponseException, Unit> {
        val textResponse = repository.find(TriggerTextResponseId(request.id)).let {
            if (it.isLeft()) return it.leftOrNull()!!.left()
            it.getOrNull()!!
        }

        request.values.content.takeUnless { it == null }?.let {
            textResponse.content = TriggerTextResponse.TriggerTextResponseContent(it)
        }

        request.values.type.takeUnless { it == null }?.let {
            textResponse.type = TriggerTextResponseContentType.fromValue(it)
        }

        return repository.save(textResponse).right()
    }

}
