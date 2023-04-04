package com.usadapekora.bot.application.trigger.create

import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.TriggerRepository

class TriggerCreator(private val repository: TriggerRepository) {

    fun create(request: TriggerCreateRequest) {
        val trigger = Trigger.fromPrimitives(
            id = request.id,
            title = request.title,
            input = request.input,
            compare = request.compare,
            outputText = request.outputText,
            discordGuildId = request.discordGuildId
        )

        try {
            repository.find(trigger.id)
            throw TriggerException.AlreadyExists("The trigger with id ${trigger.id.value} already exists")
        } catch (_: TriggerException.NotFound) {
            repository.save(trigger)
        }
    }

}
