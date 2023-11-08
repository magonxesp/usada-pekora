package com.usadapekora.bot.infraestructure.guild.mongodb

import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.guild.GuildException
import com.usadapekora.bot.domain.guild.GuildMemberMother
import com.usadapekora.bot.domain.guild.GuildMother
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.GuildDocument
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.MongoDbGuildMemberRepository
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.MongoDbGuildRepository
import com.usadapekora.shared.MongoDbRepositoryTestCase
import com.usadapekora.shared.domain.UserMother
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertIs

class MongoDbGuildRepositoryTest : MongoDbRepositoryTestCase<Guild, MongoDbGuildRepository>(
    repository = MongoDbGuildRepository(),
    mother = GuildMother
) {

    @Test
    fun `it should find guild by id`() {
        runMongoDbRepositoryTest<GuildDocument>(GuildDocument.Companion) {
            val existing = repository.find(it.id)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `it should not find by id`() {
        runMongoDbRepositoryTest<GuildDocument>(GuildDocument.Companion, save = false) {
            val result = repository.find(it.id)
            assertIs<GuildException.NotFound>(result.leftOrNull())
        }
    }

    @Test
    fun `it should find guild by provider id`() {
        runMongoDbRepositoryTest<GuildDocument>(GuildDocument.Companion) {
            val existing = repository.findByProvider(it.providerId, it.provider)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `it should not find guild by provider id`() {
        runMongoDbRepositoryTest<GuildDocument>(GuildDocument.Companion, save = false) {
            val result = repository.findByProvider(it.providerId, it.provider)
            assertIs<GuildException.NotFound>(result.leftOrNull())
        }
    }

    @Test
    fun `it should find by user id`() {
        val guilds = GuildMother.createList()
        val user = UserMother.create()
        val members = guilds.map { GuildMemberMother.create(guildId = it.id.value, userId = user.id.value) }
        val guildMemberRepository = MongoDbGuildMemberRepository()

        guilds.forEach { repository.save(it) }
        members.forEach { guildMemberRepository.save(it) }

        val result = repository.findByUserId(user.id)

        assertContentEquals(guilds.toTypedArray(), result)
    }

    @Test
    fun `it should save guild`() {
        runMongoDbRepositoryTest<GuildDocument>(GuildDocument.Companion, save = false) {
            repository.save(it)

            val existing = repository.find(it.id)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `it should update`() {
        runMongoDbRepositoryTest<GuildDocument>(GuildDocument.Companion) {
            it.name = Guild.GuildName("example change")

            repository.save(it)
            val existing = repository.find(it.id)

            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `it should delete guild preferences`() {
        runMongoDbRepositoryTest<GuildDocument>(GuildDocument.Companion, delete = false) {
            repository.delete(it)
            val result = repository.find(it.id)
            assertIs<GuildException.NotFound>(result.leftOrNull())
        }
    }
    
}
