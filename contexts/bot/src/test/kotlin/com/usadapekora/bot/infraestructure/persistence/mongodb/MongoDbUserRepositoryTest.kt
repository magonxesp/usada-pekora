package com.usadapekora.bot.infraestructure.persistence.mongodb

import com.usadapekora.bot.domain.UserMother
import com.usadapekora.bot.domain.user.User
import com.usadapekora.bot.domain.user.UserException
import com.usadapekora.bot.infraestructure.persistence.mongodb.user.MongoDbUserRepository
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class MongoDbUserRepositoryTest : MongoDbRepositoryTest<User, MongoDbUserRepository>(
    repository = MongoDbUserRepository(),
    mother = UserMother
) {

    @Test
    fun `should find user by id`() {
        databaseTest {
            val existing = repository.find(it.id)
            assertEquals(it, existing)
        }
    }

    @Test
    fun `should find user by discord id`() {
        databaseTest {
            val existing = repository.findByDiscordId(it.discordId)
            assertEquals(it, existing)
        }
    }

    @Test
    fun `should not find user by id`() {
        databaseTest(save = false) {
            assertThrows<UserException.NotFound> {
                repository.find(it.id)
            }
        }
    }

    @Test
    fun `should not find user by discord id`() {
        databaseTest(save = false) {
            assertThrows<UserException.NotFound> {
                repository.find(it.discordId)
            }
        }
    }

    @Test
    fun `should save user`() {
        databaseTest(save = false) {
            repository.save(it)
            val existing = repository.find(it.id)
            assertEquals(it, existing)
        }
    }

    @Test
    fun `should delete user`() {
        databaseTest(delete = false) {
            repository.delete(it)
            assertThrows<UserException.NotFound> {
                repository.find(it.id)
            }
        }
    }

}
