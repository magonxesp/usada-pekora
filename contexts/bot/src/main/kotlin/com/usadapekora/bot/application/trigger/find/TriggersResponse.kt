package com.usadapekora.bot.application.trigger.find

import com.usadapekora.bot.domain.trigger.Trigger

class TriggersResponse(val triggers: Array<TriggerResponse>) {
    companion object {
        fun fromArray(entities: Array<Trigger>) = TriggersResponse(
            triggers = entities.map { TriggerResponse.fromEntity(it) }.toTypedArray()
        )
    }
}
