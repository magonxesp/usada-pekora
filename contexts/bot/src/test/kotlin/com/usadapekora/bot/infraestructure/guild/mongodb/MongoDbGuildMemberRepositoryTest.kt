package com.usadapekora.bot.infraestructure.guild.mongodb

import com.usadapekora.bot.domain.guild.GuildMember
import com.usadapekora.bot.domain.guild.GuildMemberError
import com.usadapekora.bot.domain.guild.GuildMemberMother
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.GuildMemberDocument
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.MongoDbGuildMemberRepository
import com.usadapekora.shared.MongoDbRepositoryTestCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class MongoDbGuildMemberRepositoryTest : MongoDbRepositoryTestCase<GuildMember, MongoDbGuildMemberRepository>(
    repository = MongoDbGuildMemberRepository(),
    mother = GuildMemberMother
) {

    @Test
    fun `should find guild member by user and guild`() {
        runMongoDbRepositoryTest<GuildMemberDocument>(GuildMemberDocument.Companion) {
            val existing = repository.find(it.user, it.guild)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should not find guild member by user and guild`() {
        runMongoDbRepositoryTest<GuildMemberDocument>(GuildMemberDocument.Companion, save = false) {
            val result = repository.find(it.user, it.guild)
            assertIs<GuildMemberError.NotFound>(result.leftOrNull())
        }
    }

    @Test
    fun `should save guild member`() {
        runMongoDbRepositoryTest<GuildMemberDocument>(GuildMemberDocument.Companion, save = false) {
            repository.save(it)

            val existing = repository.find(it.user, it.guild)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should delete guild preferences`() {
        runMongoDbRepositoryTest<GuildMemberDocument>(GuildMemberDocument.Companion, delete = false) {
            repository.delete(it)
            val result = repository.find(it.user, it.guild)
            assertIs<GuildMemberError.NotFound>(result.leftOrNull())
        }
    }
    
}
