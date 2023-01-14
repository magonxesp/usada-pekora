package com.usadapekora.context.application.trigger.find

import com.usadapekora.context.domain.trigger.Trigger
import com.usadapekora.context.domain.trigger.TriggerException
import com.usadapekora.context.domain.trigger.TriggerMatcher
import com.usadapekora.context.domain.trigger.TriggerRepository

class TriggerFinder(
    private val repository: TriggerRepository,
    private val matcher: TriggerMatcher
) {
    fun findByInput(input: String, discordServerId: String): Trigger {
        val triggers = repository.findByDiscordServer(Trigger.TriggerDiscordGuildId(discordServerId))
        return matcher.matchInput(input, triggers) ?: throw TriggerException.NotFound()
    }
}
