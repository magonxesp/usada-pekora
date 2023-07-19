package com.usadapekora.bot.domain.trigger

import arrow.core.Either
import com.usadapekora.bot.domain.guild.Guild

interface TriggerRepository {
    fun findAll(): Array<Trigger>
    fun find(id: Trigger.TriggerId): Either<TriggerException.NotFound, Trigger>
    fun findByGuild(id: Guild.GuildId): Array<Trigger>
    fun save(entity: Trigger)
    fun delete(entity: Trigger)
}
