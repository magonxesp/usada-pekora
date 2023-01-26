package com.usadapekora.context.application.trigger.update

import com.usadapekora.context.domain.trigger.Trigger
import com.usadapekora.context.domain.trigger.TriggerRepository

class TriggerUpdater(private val repository: TriggerRepository) {

    fun update(request: TriggerUpdateRequest) {
        val trigger = repository.find(Trigger.TriggerId(request.id))

        request.values.input.takeUnless { it == null }?.let {
            trigger.input = Trigger.TriggerInput(it)
        }

        request.values.compare.takeUnless { it == null }?.let {
            trigger.compare = Trigger.TriggerCompare.fromValue(it)
        }

        request.values.outputText.let {
            trigger.outputText = Trigger.TriggerOutputText(it)
        }

        request.values.discordGuildId.takeUnless { it == null }?.let {
            trigger.discordGuildId = Trigger.TriggerDiscordGuildId(it)
        }

        repository.save(trigger)
    }

}
