package com.usadapekora.bot.infraestructure.persistence.mongodb

import com.usadapekora.bot.domain.GuildPreferencesMother
import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.bot.domain.guild.GuildPreferencesException
import com.usadapekora.bot.infraestructure.persistence.mongodb.guild.MongoDbGuildPreferencesRepository
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertTrue

class MongoDbGuildPreferencesTest : MongoDbRepositoryTest<GuildPreferences, MongoDbGuildPreferencesRepository>(
    repository = MongoDbGuildPreferencesRepository(), 
    mother = GuildPreferencesMother
) {

    @Test
    fun `should find guild preferences by guild discord id`() {
        databaseTest {
            val existing = repository.findByGuildId(it.guildId)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should not find guild preferences by guild discord id`() {
        databaseTest(save = false) {
            val result = repository.findByGuildId(it.guildId)
            assertTrue(result.leftOrNull() is GuildPreferencesException.NotFound)
        }
    }

    @Test
    fun `should save guild preferences`() {
        databaseTest(save = false) {
            repository.save(it)

            val existing = repository.findByGuildId(it.guildId)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should update guild preferences`() {
        databaseTest {
            it.preferences[GuildPreferences.GuildPreference.FeedChannelId] = Random().nextLong().toString()

            repository.save(it)
            val existing = repository.findByGuildId(it.guildId)

            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should delete guild preferences`() {
        databaseTest(delete = false) {
            repository.delete(it)
            val result = repository.findByGuildId(it.guildId)
            assertTrue(result.leftOrNull() is GuildPreferencesException.NotFound)
        }
    }
    
}
