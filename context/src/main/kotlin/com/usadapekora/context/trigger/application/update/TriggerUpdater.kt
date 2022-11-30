package com.usadapekora.context.trigger.application.update

import com.usadapekora.context.trigger.domain.Trigger
import com.usadapekora.context.trigger.domain.TriggerRepository

class TriggerUpdater(private val repository: TriggerRepository) {

    fun update(request: TriggerUpdateRequest) {
        val trigger = repository.find(Trigger.TriggerId(request.id))

        request.input.takeUnless { it == null }?.let {
            trigger.input = Trigger.TriggerInput(it)
        }

        request.compare.takeUnless { it == null }?.let {
            trigger.compare = Trigger.TriggerCompare.fromValue(it)
        }

        request.outputText.let {
            trigger.outputText = Trigger.TriggerOutputText(it)
        }

        request.discordGuildId.takeUnless { it == null }?.let {
            trigger.discordGuildId = Trigger.TriggerDiscordGuildId(it)
        }

        repository.save(trigger)
    }

}
