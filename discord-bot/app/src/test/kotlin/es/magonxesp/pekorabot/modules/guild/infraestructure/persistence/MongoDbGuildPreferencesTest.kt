package es.magonxesp.pekorabot.modules.guild.infraestructure.persistence

import es.magonxesp.pekorabot.modules.guild.GuildModuleIntegrationTest
import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferences
import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferencesException
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.Test

class MongoDbGuildPreferencesTest : GuildModuleIntegrationTest() {

    @Test
    fun `should find guild preferences by guild discord id`() {
        val repository = MongoDbGuildPreferencesRepository()

        databaseTestGuildPreferences {
            val existing = repository.findByGuildId(it.guildId)
            assertEquals(it, existing)
        }
    }

    @Test
    fun `should not find guild preferences by guild discord id`() {
        val repository = MongoDbGuildPreferencesRepository()

        databaseTestGuildPreferences(save = false) {
            assertThrows<GuildPreferencesException.NotFound> {
                repository.findByGuildId(it.guildId)
            }
        }
    }

    @Test
    fun `should save guild preferences`() {
        val repository = MongoDbGuildPreferencesRepository()

        databaseTestGuildPreferences(save = false) {
            repository.save(it)

            val existing = repository.findByGuildId(it.guildId)
            assertEquals(it, existing)
        }
    }

    @Test
    fun `should update guild preferences`() {
        val repository = MongoDbGuildPreferencesRepository()

        databaseTestGuildPreferences() {
            it.preferences[GuildPreferences.GuildPreference.FeedChannelId] = Random().nextLong()

            repository.save(it)
            val existing = repository.findByGuildId(it.guildId)

            assertEquals(it, existing)
        }
    }

    @Test
    fun `should delete guild preferences`() {
        val repository = MongoDbGuildPreferencesRepository()

        databaseTestGuildPreferences(delete = false) {
            repository.delete(it)

            assertThrows<GuildPreferencesException.NotFound> {
                repository.findByGuildId(it.guildId)
            }
        }
    }
    
}
