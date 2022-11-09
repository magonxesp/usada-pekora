package com.usadapekora.context.user.infraestructure.persistence

import com.usadapekora.context.user.UserModuleIntegrationTest
import com.usadapekora.context.user.domain.UserException
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class MongoDbUserRepositoryTest : UserModuleIntegrationTest() {

    @Test
    fun `should find user by id`() {
        val repository = MongoDbUserRepository()

        databaseTestUser {
            val existing = repository.find(it.id)
            assertEquals(it, existing)
        }
    }

    @Test
    fun `should find user by discord id`() {
        val repository = MongoDbUserRepository()

        databaseTestUser {
            val existing = repository.findByDiscordId(it.discordId)
            assertEquals(it, existing)
        }
    }

    @Test
    fun `should not find user by id`() {
        val repository = MongoDbUserRepository()

        databaseTestUser(save = false) {
            assertThrows<UserException.NotFound> {
                repository.find(it.id)
            }
        }
    }

    @Test
    fun `should not find user by discord id`() {
        val repository = MongoDbUserRepository()

        databaseTestUser(save = false) {
            assertThrows<UserException.NotFound> {
                repository.find(it.discordId)
            }
        }
    }

    @Test
    fun `should save user`() {
        val repository = MongoDbUserRepository()

        databaseTestUser(save = false) {
            repository.save(it)

            val existing = repository.find(it.id)
            assertEquals(it, existing)
        }
    }

    @Test
    fun `should delete user`() {
        val repository = MongoDbUserRepository()

        databaseTestUser(delete = false) {
            repository.delete(it)

            assertThrows<UserException.NotFound> {
                repository.find(it.id)
            }
        }
    }

}
