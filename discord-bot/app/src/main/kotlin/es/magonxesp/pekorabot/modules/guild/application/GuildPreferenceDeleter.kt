package es.magonxesp.pekorabot.modules.guild.application

import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferences
import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferencesException
import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferencesRepository

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
