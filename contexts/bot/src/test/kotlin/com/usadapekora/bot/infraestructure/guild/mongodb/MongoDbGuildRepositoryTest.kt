package com.usadapekora.bot.infraestructure.guild.mongodb

import com.usadapekora.bot.domain.guild.*
import com.usadapekora.bot.infraestructure.MongoDbRepositoryTestCase
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.MongoDbGuildPreferencesRepository
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.MongoDbGuildRepository
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

class MongoDbGuildRepositoryTest : MongoDbRepositoryTestCase<Guild, MongoDbGuildRepository>(
    repository = MongoDbGuildRepository(),
    mother = GuildMother
) {

    @Test
    fun `should find guild by id`() {
        databaseTest {
            val existing = repository.find(it.id)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should not find by id`() {
        databaseTest(save = false) {
            val result = repository.find(it.id)
            assertIs<GuildError.NotFound>(result.leftOrNull())
        }
    }

    @Test
    fun `should find guild by provider id`() {
        databaseTest {
            val existing = repository.findByProvider(it.providerId, it.provider)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should not find guild by provider id`() {
        databaseTest(save = false) {
            val result = repository.findByProvider(it.providerId, it.provider)
            assertIs<GuildError.NotFound>(result.leftOrNull())
        }
    }

    @Test
    fun `should save guild`() {
        databaseTest(save = false) {
            repository.save(it)

            val existing = repository.find(it.id)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should update`() {
        databaseTest {
            it.name = Guild.GuildName("example change")

            repository.save(it)
            val existing = repository.find(it.id)

            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should delete guild preferences`() {
        databaseTest(delete = false) {
            repository.delete(it)
            val result = repository.find(it.id)
            assertIs<GuildError.NotFound>(result.leftOrNull())
        }
    }
    
}
