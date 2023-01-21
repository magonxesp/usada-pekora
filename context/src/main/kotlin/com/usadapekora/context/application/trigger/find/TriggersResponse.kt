package com.usadapekora.context.application.trigger.find

import com.usadapekora.context.domain.trigger.Trigger

class TriggersResponse(val triggers: Array<TriggerResponse>) {
    companion object {
        fun fromArray(entities: Array<Trigger>) = TriggersResponse(
            triggers = entities.map { TriggerResponse.fromEntity(it) }.toTypedArray()
        )
    }
}
