package com.usadapekora.context.trigger.application

import com.usadapekora.context.trigger.domain.Trigger
import com.usadapekora.context.trigger.domain.TriggerException
import com.usadapekora.context.trigger.domain.TriggerMatcher
import com.usadapekora.context.trigger.domain.TriggerRepository

class TriggerFinder(
    private val repository: TriggerRepository,
    private val matcher: TriggerMatcher
) {
    suspend fun findByInput(input: String, discordServerId: String): Trigger {
        val triggers = repository.findByDiscordServer(Trigger.TriggerDiscordGuildId(discordServerId))
        return matcher.matchInput(input, triggers) ?: throw TriggerException.NotFound()
    }
}
