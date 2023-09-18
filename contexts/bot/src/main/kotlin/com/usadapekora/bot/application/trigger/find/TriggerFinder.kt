package com.usadapekora.bot.application.trigger.find

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.trigger.*

class TriggerFinder(
    private val repository: TriggerRepository,
    private val matcher: TriggerMatcher,
    private val triggerMonitoring: TriggerMonitoring
) {
    fun findByInput(input: String, guildId: String): Either<TriggerException.NotFound, TriggerResponse> {
        val triggers = repository.findByGuild(Guild.GuildId(guildId))
        val matchingTrigger = matcher.matchInput(input, triggers)

        if (matchingTrigger == null) {
            triggerMonitoring.triggerInputNotMatched()
            return TriggerException.NotFound("Trigger not found for input $input").left()
        }

        triggerMonitoring.triggerInputMatched()
        return TriggerResponse.fromEntity(matchingTrigger).right()
    }

    fun find(id: String): Either<TriggerException, TriggerResponse> {
        val triggerId = Trigger.TriggerId(id)
        val trigger = repository.find(triggerId)
                .onLeft { return it.left() }
                .getOrNull()!!

        return TriggerResponse.fromEntity(trigger).right()
    }

    fun findByGuild(guildId: String): TriggersResponse
        = repository.findByGuild(Guild.GuildId(guildId)).let {
            TriggersResponse.fromArray(it)
        }
}
