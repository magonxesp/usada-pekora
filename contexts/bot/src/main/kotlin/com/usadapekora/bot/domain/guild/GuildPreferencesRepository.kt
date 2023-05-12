package com.usadapekora.bot.domain.guild

import arrow.core.Either

interface GuildPreferencesRepository {
    fun findByGuildId(guildId: String): Either<GuildPreferencesException.NotFound, GuildPreferences>
    fun findByPreference(preference: GuildPreferences.GuildPreference): Array<GuildPreferences>
    fun save(entity: GuildPreferences)
    fun delete(entity: GuildPreferences)
}
