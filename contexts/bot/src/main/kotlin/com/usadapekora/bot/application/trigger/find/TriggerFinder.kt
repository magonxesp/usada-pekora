package com.usadapekora.bot.application.trigger.find

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.TriggerMatcher
import com.usadapekora.bot.domain.trigger.TriggerRepository

class TriggerFinder(
    private val repository: TriggerRepository,
    private val matcher: TriggerMatcher
) {
    fun findByInput(input: String, guildId: String): TriggerResponse {
        val triggers = repository.findByGuild(Guild.GuildId(guildId))
        return TriggerResponse.fromEntity(matcher.matchInput(input, triggers) ?: throw TriggerException.NotFound())
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
