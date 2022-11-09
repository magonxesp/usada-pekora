package com.usadapekora.context.guild.domain

interface GuildPreferencesRepository {
    fun findByGuildId(guildId: String): GuildPreferences
    fun findByPreference(preference: GuildPreferences.GuildPreference): Array<GuildPreferences>
    fun save(guildPreferences: GuildPreferences)
    fun delete(guildPreferences: GuildPreferences)
}
