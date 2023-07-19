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
    fun findByInput(input: String, discordServerId: String): TriggerResponse {
        val triggers = repository.findByGuild(Guild.GuildId(discordServerId))
        return TriggerResponse.fromEntity(matcher.matchInput(input, triggers) ?: throw TriggerException.NotFound())
    }

    fun find(id: String): Either<TriggerException, TriggerResponse> {
        val trigger = repository.find(Trigger.TriggerId(id))

        if (trigger.isLeft()) {
            return trigger.leftOrNull()!!.left()
        }

        return TriggerResponse.fromEntity(trigger.getOrNull()!!).right()
    }

    fun findByDiscordServer(discordServerId: String): TriggersResponse
        = repository.findByGuild(Guild.GuildId(discordServerId)).let {
            TriggersResponse.fromArray(it)
        }
}
