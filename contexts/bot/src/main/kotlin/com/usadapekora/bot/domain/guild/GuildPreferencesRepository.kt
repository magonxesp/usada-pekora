package com.usadapekora.bot.domain.guild

interface GuildPreferencesRepository {
    fun findByGuildId(guildId: String): GuildPreferences
    fun findByPreference(preference: GuildPreferences.GuildPreference): Array<GuildPreferences>
    fun save(entity: GuildPreferences)
    fun delete(entity: GuildPreferences)
}
