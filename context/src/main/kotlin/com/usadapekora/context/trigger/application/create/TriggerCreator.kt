package com.usadapekora.context.trigger.application.create

import com.usadapekora.context.trigger.domain.Trigger
import com.usadapekora.context.trigger.domain.TriggerRepository

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
