package es.magonxesp.pekorabot.modules.guild.application

import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferences
import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferencesException
import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferencesRepository

class GuildPreferenceCreator(private val repository: GuildPreferencesRepository) {

    fun create(guildId: String, preference: GuildPreferences.GuildPreference, value: Any) {
        val preferences = try {
            repository.findByGuildId(guildId)
        } catch (exception: GuildPreferencesException.NotFound) {
            GuildPreferences(guildId)
        }

        preferences.preferences[preference] = value
        repository.save(preferences)
    }

}
