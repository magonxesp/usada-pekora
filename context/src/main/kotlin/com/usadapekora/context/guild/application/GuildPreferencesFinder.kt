package com.usadapekora.context.guild.application

import com.usadapekora.context.guild.domain.GuildPreferences
import com.usadapekora.context.guild.domain.GuildPreferencesRepository

class GuildPreferencesFinder(private val repository: GuildPreferencesRepository) {

    fun find(guildId: String) = repository.findByGuildId(guildId)
    fun findByPreference(preference: GuildPreferences.GuildPreference) = repository.findByPreference(preference)
}
