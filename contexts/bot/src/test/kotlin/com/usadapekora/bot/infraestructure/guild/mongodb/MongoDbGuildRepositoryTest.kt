package com.usadapekora.bot.infraestructure.guild.mongodb

import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.guild.GuildError
import com.usadapekora.bot.domain.guild.GuildMother
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.GuildDocument
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.MongoDbGuildRepository
import com.usadapekora.shared.MongoDbRepositoryTestCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class MongoDbGuildRepositoryTest : MongoDbRepositoryTestCase<Guild, MongoDbGuildRepository>(
    repository = MongoDbGuildRepository(),
    mother = GuildMother
) {

    @Test
    fun `should find guild by id`() {
        runMongoDbRepositoryTest<GuildDocument>(GuildDocument.Companion) {
            val existing = repository.find(it.id)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should not find by id`() {
        runMongoDbRepositoryTest<GuildDocument>(GuildDocument.Companion, save = false) {
            val result = repository.find(it.id)
            assertIs<GuildError.NotFound>(result.leftOrNull())
        }
    }

    @Test
    fun `should find guild by provider id`() {
        runMongoDbRepositoryTest<GuildDocument>(GuildDocument.Companion) {
            val existing = repository.findByProvider(it.providerId, it.provider)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should not find guild by provider id`() {
        runMongoDbRepositoryTest<GuildDocument>(GuildDocument.Companion, save = false) {
            val result = repository.findByProvider(it.providerId, it.provider)
            assertIs<GuildError.NotFound>(result.leftOrNull())
        }
    }

    @Test
    fun `should save guild`() {
        runMongoDbRepositoryTest<GuildDocument>(GuildDocument.Companion, save = false) {
            repository.save(it)

            val existing = repository.find(it.id)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should update`() {
        runMongoDbRepositoryTest<GuildDocument>(GuildDocument.Companion) {
            it.name = Guild.GuildName("example change")

            repository.save(it)
            val existing = repository.find(it.id)

            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should delete guild preferences`() {
        runMongoDbRepositoryTest<GuildDocument>(GuildDocument.Companion, delete = false) {
            repository.delete(it)
            val result = repository.find(it.id)
            assertIs<GuildError.NotFound>(result.leftOrNull())
        }
    }
    
}
