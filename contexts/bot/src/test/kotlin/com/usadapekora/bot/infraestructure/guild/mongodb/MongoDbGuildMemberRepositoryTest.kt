package com.usadapekora.bot.infraestructure.guild.mongodb

import com.usadapekora.bot.domain.guild.*
import com.usadapekora.bot.infraestructure.MongoDbRepositoryTestCase
import com.usadapekora.bot.infraestructure.guild.persistence.mongodb.MongoDbGuildMemberRepository
import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertIs

class MongoDbGuildMemberRepositoryTest : MongoDbRepositoryTestCase<GuildMember, MongoDbGuildMemberRepository>(
    repository = MongoDbGuildMemberRepository(),
    mother = GuildMemberMother
) {

    @Test
    fun `should find guild member by user and guild`() {
        databaseTest {
            val existing = repository.find(it.user, it.guild)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should not find guild member by user and guild`() {
        databaseTest(save = false) {
            val result = repository.find(it.user, it.guild)
            assertIs<GuildMemberError.NotFound>(result.leftOrNull())
        }
    }

    @Test
    fun `should save guild member`() {
        databaseTest(save = false) {
            repository.save(it)

            val existing = repository.find(it.user, it.guild)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should delete guild preferences`() {
        databaseTest(delete = false) {
            repository.delete(it)
            val result = repository.find(it.user, it.guild)
            assertIs<GuildMemberError.NotFound>(result.leftOrNull())
        }
    }
    
}
