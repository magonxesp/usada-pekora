package com.usadapekora.bot.application.trigger.delete

import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerRepository

class TriggerDeleter(private val repository: TriggerRepository) {

    fun delete(id: String) {
        val trigger = repository.find(Trigger.TriggerId(id))
        repository.delete(trigger)
    }

}
