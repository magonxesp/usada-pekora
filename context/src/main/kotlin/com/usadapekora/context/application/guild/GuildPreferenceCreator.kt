package com.usadapekora.context.application.guild

import com.usadapekora.context.domain.guild.GuildPreferences
import com.usadapekora.context.domain.guild.GuildPreferencesException
import com.usadapekora.context.domain.guild.GuildPreferencesRepository

class GuildPreferenceCreator(private val repository: GuildPreferencesRepository) {

    fun create(guildId: String, preference: GuildPreferences.GuildPreference, value: String) {
        val preferences = try {
            repository.findByGuildId(guildId)
        } catch (exception: GuildPreferencesException.NotFound) {
            GuildPreferences(guildId)
        }

        preferences.preferences[preference] = value
        repository.save(preferences)
    }

}
