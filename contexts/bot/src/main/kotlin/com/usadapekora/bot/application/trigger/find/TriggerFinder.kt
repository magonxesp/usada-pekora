package com.usadapekora.bot.application.trigger.find

import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.TriggerMatcher
import com.usadapekora.bot.domain.trigger.TriggerRepository

class TriggerFinder(
    private val repository: TriggerRepository,
    private val matcher: TriggerMatcher
) {
    fun findByInput(input: String, discordServerId: String): TriggerResponse {
        val triggers = repository.findByDiscordServer(Trigger.TriggerDiscordGuildId(discordServerId))
        return TriggerResponse.fromEntity(matcher.matchInput(input, triggers) ?: throw TriggerException.NotFound())
    }

    fun find(id: String): TriggerResponse
        = TriggerResponse.fromEntity(repository.find(Trigger.TriggerId(id)))

    fun findByDiscordServer(discordServerId: String): TriggersResponse
        = TriggersResponse.fromArray(repository.findByDiscordServer(Trigger.TriggerDiscordGuildId(discordServerId)))
}