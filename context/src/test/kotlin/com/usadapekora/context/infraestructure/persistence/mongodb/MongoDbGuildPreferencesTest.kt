package com.usadapekora.context.infraestructure.persistence.mongodb

import com.usadapekora.context.domain.GuildPreferencesMother
import com.usadapekora.context.domain.guild.GuildPreferences
import com.usadapekora.context.domain.guild.GuildPreferencesException
import com.usadapekora.context.infraestructure.persistence.mongodb.guild.MongoDbGuildPreferencesRepository
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.Test

class MongoDbGuildPreferencesTest : MongoDbRepositoryTest<GuildPreferences, MongoDbGuildPreferencesRepository>(
    repository = MongoDbGuildPreferencesRepository(), 
    mother = GuildPreferencesMother
) {

    @Test
    fun `should find guild preferences by guild discord id`() {
        databaseTest {
            val existing = repository.findByGuildId(it.guildId)
            assertEquals(it, existing)
        }
    }

    @Test
    fun `should not find guild preferences by guild discord id`() {
        databaseTest(save = false) {
            assertThrows<GuildPreferencesException.NotFound> {
                repository.findByGuildId(it.guildId)
            }
        }
    }

    @Test
    fun `should save guild preferences`() {
        databaseTest(save = false) {
            repository.save(it)

            val existing = repository.findByGuildId(it.guildId)
            assertEquals(it, existing)
        }
    }

    @Test
    fun `should update guild preferences`() {
        databaseTest {
            it.preferences[GuildPreferences.GuildPreference.FeedChannelId] = Random().nextLong().toString()

            repository.save(it)
            val existing = repository.findByGuildId(it.guildId)

            assertEquals(it, existing)
        }
    }

    @Test
    fun `should delete guild preferences`() {
        databaseTest(delete = false) {
            repository.delete(it)

            assertThrows<GuildPreferencesException.NotFound> {
                repository.findByGuildId(it.guildId)
            }
        }
    }
    
}
