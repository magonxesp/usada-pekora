package com.usadapekora.context.application.trigger.delete

import com.usadapekora.context.domain.trigger.Trigger
import com.usadapekora.context.domain.trigger.TriggerRepository

class TriggerDeleter(private val repository: TriggerRepository) {

    fun delete(id: String) {
        val trigger = repository.find(Trigger.TriggerId(id))
        repository.delete(trigger)
    }

}
