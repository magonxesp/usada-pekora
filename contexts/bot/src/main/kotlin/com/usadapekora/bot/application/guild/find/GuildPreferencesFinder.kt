package com.usadapekora.bot.application.guild.find

import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.bot.domain.guild.GuildPreferencesRepository

class GuildPreferencesFinder(private val repository: GuildPreferencesRepository) {
    fun find(guildId: String) = repository.findByGuildId(guildId)
    fun findByPreference(preference: GuildPreferences.GuildPreference) = repository.findByPreference(preference)
}
