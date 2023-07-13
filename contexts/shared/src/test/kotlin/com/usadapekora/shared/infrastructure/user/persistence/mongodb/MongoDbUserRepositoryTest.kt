package com.usadapekora.shared.infrastructure.user.persistence.mongodb

import com.usadapekora.shared.MongoDbRepositoryTestCase
import com.usadapekora.shared.domain.UserMother
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.domain.user.UserException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MongoDbUserRepositoryTest : MongoDbRepositoryTestCase<User, MongoDbUserRepository>(
    repository = MongoDbUserRepository(),
    mother = UserMother
) {

    @Test
    fun `should find user by id`() {
        runMongoDbRepositoryTest<UserDocument>(UserDocument.Companion)  {
            val existing = repository.find(it.id).getOrNull()
            assertEquals(it, existing)
        }
    }

    @Test
    fun `should find user by discord id`() {
        runMongoDbRepositoryTest<UserDocument>(UserDocument.Companion) {
            val existing = repository.findByDiscordId(it.providerId).getOrNull()
            assertEquals(it, existing)
        }
    }

    @Test
    fun `should not find user by id`() {
        runMongoDbRepositoryTest<UserDocument>(UserDocument.Companion, save = false) {
            val result = repository.find(it.id)
            assertTrue(result.leftOrNull() is UserException.NotFound)
        }
    }

    @Test
    fun `should not find user by discord id`() {
        runMongoDbRepositoryTest<UserDocument>(UserDocument.Companion, save = false) {
            val result = repository.findByDiscordId(it.providerId)
            assertTrue(result.leftOrNull() is UserException.NotFound)
        }
    }

    @Test
    fun `should save user`() {
        runMongoDbRepositoryTest<UserDocument>(UserDocument.Companion, save = false) {
            repository.save(it)
            val existing = repository.find(it.id)
            assertEquals(it, existing.getOrNull())
        }
    }

    @Test
    fun `should delete user`() {
        runMongoDbRepositoryTest<UserDocument>(UserDocument.Companion, delete = false) {
            repository.delete(it)

            val result = repository.find(it.id)
            assertTrue(result.leftOrNull() is UserException.NotFound)
        }
    }

}
