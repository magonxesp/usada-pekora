package com.usadapekora.context.application.guild

import com.usadapekora.context.domain.guild.GuildPreferences
import com.usadapekora.context.domain.guild.GuildPreferencesRepository

class GuildPreferencesFinder(private val repository: GuildPreferencesRepository) {

    fun find(guildId: String) = repository.findByGuildId(guildId)
    fun findByPreference(preference: GuildPreferences.GuildPreference) = repository.findByPreference(preference)
}
