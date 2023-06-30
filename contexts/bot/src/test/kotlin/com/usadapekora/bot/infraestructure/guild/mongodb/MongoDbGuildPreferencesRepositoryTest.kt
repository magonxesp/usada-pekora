package com.usadapekora.bot.infraestructure.guild.mongodb

import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.bot.domain.guild.GuildPreferencesException
import com.usadapekora.bot.domain.guild.GuildPreferencesMother
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.GuildPreferencesDocument
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.MongoDbGuildPreferencesRepository
import com.usadapekora.shared.MongoDbRepositoryTestCase
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MongoDbGuildPreferencesRepositoryTest : MongoDbRepositoryTestCase<GuildPreferences, MongoDbGuildPreferencesRepository>(
    repository = MongoDbGuildPreferencesRepository(),
    mother = GuildPreferencesMother
) {

    @Test
    fun `should find guild preferences by guild discord id`() {
        runMongoDbRepositoryTest<GuildPreferencesDocument>(GuildPreferencesDocument.Companion) {
            val existing = repository.findByGuildId(it.guildId)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should not find guild preferences by guild discord id`() {
        runMongoDbRepositoryTest<GuildPreferencesDocument>(GuildPreferencesDocument.Companion, save = false) {
            val result = repository.findByGuildId(it.guildId)
            assertTrue(result.leftOrNull() is GuildPreferencesException.NotFound)
        }
    }

    @Test
    fun `should save guild preferences`() {
        runMongoDbRepositoryTest<GuildPreferencesDocument>(GuildPreferencesDocument.Companion, save = false) {
            repository.save(it)

            val existing = repository.findByGuildId(it.guildId)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should update guild preferences`() {
        runMongoDbRepositoryTest<GuildPreferencesDocument>(GuildPreferencesDocument.Companion) {
            it.preferences[GuildPreferences.GuildPreference.FeedChannelId] = Random().nextLong().toString()

            repository.save(it)
            val existing = repository.findByGuildId(it.guildId)

            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should delete guild preferences`() {
        runMongoDbRepositoryTest<GuildPreferencesDocument>(GuildPreferencesDocument.Companion, delete = false) {
            repository.delete(it)
            val result = repository.findByGuildId(it.guildId)
            assertTrue(result.leftOrNull() is GuildPreferencesException.NotFound)
        }
    }
    
}
