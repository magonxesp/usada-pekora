package com.usadapekora.bot.application.trigger.delete.text

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseId
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository

class TriggerTextResponseDeleter(private val repository: TriggerTextResponseRepository) {

    fun delete(id: String): Either<TriggerTextResponseException, Unit> {
        val result = repository.find(TriggerTextResponseId(id))

        if (result.leftOrNull() != null) {
            return result.leftOrNull()!!.left()
        }

        return repository.delete(result.getOrNull()!!).right()
    }

}
