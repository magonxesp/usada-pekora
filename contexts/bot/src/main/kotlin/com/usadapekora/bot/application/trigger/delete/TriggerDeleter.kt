package com.usadapekora.bot.application.trigger.delete

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.TriggerRepository

class TriggerDeleter(private val repository: TriggerRepository) {

    fun delete(id: String): Either<TriggerException, Unit> {
        val trigger = repository.find(Trigger.TriggerId(id))

        if (trigger.isLeft()) {
            return trigger.leftOrNull()!!.left()
        }

        return repository.delete(trigger.getOrNull()!!).right()
    }

}
