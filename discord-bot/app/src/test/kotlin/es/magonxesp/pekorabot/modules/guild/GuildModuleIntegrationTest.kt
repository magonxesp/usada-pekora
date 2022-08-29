package es.magonxesp.pekorabot.modules.guild

import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferences
import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferencesMother
import es.magonxesp.pekorabot.modules.guild.infraestructure.persistence.MongoDbGuildPreferencesRepository

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
