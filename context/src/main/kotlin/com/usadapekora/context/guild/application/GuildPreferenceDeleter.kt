package com.usadapekora.context.guild.application

import com.usadapekora.context.guild.domain.GuildPreferences
import com.usadapekora.context.guild.domain.GuildPreferencesException
import com.usadapekora.context.guild.domain.GuildPreferencesRepository

class GuildPreferenceDeleter(private val repository: GuildPreferencesRepository) {

    fun delete(guildId: String, preference: GuildPreferences.GuildPreference) {
        try {
            val preferences = repository.findByGuildId(guildId)
            preferences.preferences.remove(preference)
            repository.save(preferences)
        } catch (_: GuildPreferencesException.NotFound) {

        }
    }

}
