package com.usadapekora.bot.application.trigger.find

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.trigger.*

class TriggerFinder(
    private val repository: TriggerRepository,
    private val builtInRepository: BuiltInTriggerRepository,
    private val matcher: TriggerMatcher
) {
    private fun findByGuildOverrideFiltered(guildId: String): Array<Trigger> {
        val builtInTriggers = builtInRepository.findAll()
        val triggers = repository.findByGuild(Guild.GuildId(guildId))

        return arrayOf(
            *builtInTriggers
                .filter { builtIn -> triggers.none { it.overrides == builtIn.id } }
                .toTypedArray(),
            *triggers
        )
    }

    fun findByInput(input: String, guildId: String): TriggerResponse {
        val triggers = findByGuildOverrideFiltered(guildId)
        return TriggerResponse.fromEntity(matcher.matchInput(input, triggers) ?: throw TriggerException.NotFound())
    }

    fun find(id: String): Either<TriggerException, TriggerResponse> {
        val trigger = repository.find(Trigger.TriggerId(id))

        if (trigger.isLeft()) {
            return trigger.leftOrNull()!!.left()
        }

        return TriggerResponse.fromEntity(trigger.getOrNull()!!).right()
    }

    fun findByGuild(guildId: String): TriggersResponse
        = findByGuildOverrideFiltered(guildId).let {
            TriggersResponse.fromArray(it)
        }
}
