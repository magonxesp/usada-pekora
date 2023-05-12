package com.usadapekora.bot.infraestructure.persistence.mongodb

import com.usadapekora.bot.domain.UserMother
import com.usadapekora.bot.domain.user.User
import com.usadapekora.bot.domain.user.UserException
import com.usadapekora.bot.infraestructure.persistence.mongodb.user.MongoDbUserRepository
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MongoDbUserRepositoryTest : MongoDbRepositoryTest<User, MongoDbUserRepository>(
    repository = MongoDbUserRepository(),
    mother = UserMother
) {

    @Test
    fun `should find user by id`() {
        databaseTest {
            val existing = repository.find(it.id).getOrNull()
            assertEquals(it, existing)
        }
    }

    @Test
    fun `should find user by discord id`() {
        databaseTest {
            val existing = repository.findByDiscordId(it.discordId).getOrNull()
            assertEquals(it, existing)
        }
    }

    @Test
    fun `should not find user by id`() {
        databaseTest(save = false) {
            val result = repository.find(it.id)
            assertTrue(result.leftOrNull() is UserException.NotFound)
        }
    }

    @Test
    fun `should not find user by discord id`() {
        databaseTest(save = false) {
            val result = repository.findByDiscordId(it.discordId)
            assertTrue(result.leftOrNull() is UserException.NotFound)
        }
    }

    @Test
    fun `should save user`() {
        databaseTest(save = false) {
            repository.save(it)
            val existing = repository.find(it.id)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should delete user`() {
        databaseTest(delete = false) {
            repository.delete(it)

            val result = repository.find(it.id)
            assertTrue(result.leftOrNull() is UserException.NotFound)
        }
    }

}
