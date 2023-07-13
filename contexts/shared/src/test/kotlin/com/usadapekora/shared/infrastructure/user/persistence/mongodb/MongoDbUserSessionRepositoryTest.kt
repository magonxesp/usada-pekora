package com.usadapekora.shared.infrastructure.user.persistence.mongodb

import com.usadapekora.shared.MongoDbRepositoryTestCase
import com.usadapekora.shared.domain.UserSessionMother
import com.usadapekora.shared.domain.user.UserSession
import com.usadapekora.shared.domain.user.UserSessionError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class MongoDbUserSessionRepositoryTest : MongoDbRepositoryTestCase<UserSession, MongoDbUserSessionRepository>(
    repository = MongoDbUserSessionRepository(),
    mother = UserSessionMother
) {

    @Test
    fun `it should find a user session`() {
        runMongoDbRepositoryTest(UserSessionDocument.Companion) {
            val result = repository.find(it.id)
            assertEquals(it, result.getOrNull())
        }
    }

    @Test
    fun `it should not find a user session`() {
        runMongoDbRepositoryTest(UserSessionDocument.Companion, save = false) {
            val result = repository.find(it.id)
            assertIs<UserSessionError.NotFound>(result.leftOrNull())
        }
    }

    @Test
    fun `it should save new user session`() {
        runMongoDbRepositoryTest(UserSessionDocument.Companion, save = false) {
            repository.save(it).run {
                assertIs<Unit>(getOrNull(), leftOrNull()?.message)
            }

            val result = repository.find(it.id)
            assertEquals(it, result.getOrNull())
        }
    }

    @Test
    fun `it should save existing user session`() {
        runMongoDbRepositoryTest(UserSessionDocument.Companion) {
            val modified = it.copy(device = UserSession.UserSessionDevice("iPhone"))

            repository.save(modified).run {
                assertIs<Unit>(getOrNull(), leftOrNull()?.message)
            }

            val result = repository.find(it.id)
            assertEquals(modified, result.getOrNull())
        }
    }
}
