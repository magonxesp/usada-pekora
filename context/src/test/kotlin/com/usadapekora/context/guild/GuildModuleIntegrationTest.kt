package com.usadapekora.context.guild

import com.usadapekora.context.guild.domain.GuildPreferences
import com.usadapekora.context.guild.domain.GuildPreferencesMother
import com.usadapekora.context.guild.infraestructure.persistence.MongoDbGuildPreferencesRepository

open class GuildModuleIntegrationTest {

    private val repository = MongoDbGuildPreferencesRepository()

    /**
     * Creates a test user and delete from the database after test
     */
    protected fun databaseTestGuildPreferences(
        guildPreferences: GuildPreferences = GuildPreferencesMother.create(),
        save: Boolean = true,
        delete: Boolean = true,
        test: (guildPreferences: GuildPreferences) -> Unit
    ) {
        if (save) {
            repository.save(guildPreferences)
        }

        test(guildPreferences)

        if (delete) {
            repository.delete(guildPreferences)
        }
    }

}
