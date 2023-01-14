package com.usadapekora.context.application.guild

import com.usadapekora.context.domain.guild.GuildPreferences
import com.usadapekora.context.domain.guild.GuildPreferencesException
import com.usadapekora.context.domain.guild.GuildPreferencesRepository

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
