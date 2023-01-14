package com.usadapekora.context.application.trigger.create

import com.usadapekora.context.domain.trigger.Trigger
import com.usadapekora.context.domain.trigger.TriggerRepository

class TriggerCreator(private val repository: TriggerRepository) {

    fun create(request: TriggerCreateRequest) {
        val trigger = Trigger.fromPrimitives(
            id = request.id,
            input = request.input,
            compare = request.compare,
            outputText = request.outputText,
            discordGuildId = request.discordGuildId
        )

        repository.save(trigger)
    }

}
