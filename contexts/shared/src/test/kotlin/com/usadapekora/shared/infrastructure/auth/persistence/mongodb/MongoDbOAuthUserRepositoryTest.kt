package com.usadapekora.shared.infrastructure.auth.persistence.mongodb

import com.usadapekora.shared.MongoDbRepositoryTestCase
import com.usadapekora.shared.domain.OAuthUserMother
import com.usadapekora.shared.domain.auth.OAuthUser
import com.usadapekora.shared.domain.user.User
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class MongoDbOAuthUserRepositoryTest : MongoDbRepositoryTestCase<OAuthUser, MongoDbOAuthUserRepository>(
    repository = MongoDbOAuthUserRepository(),
    mother = OAuthUserMother
) {

    @Test
    fun `it should find a oauth user by user id`() {
        runMongoDbRepositoryTest<OAuthUserDocument>(OAuthUserDocument.Companion) {
            val result = repository.find(User.UserId(it.userId))
            assertEquals(it, result.getOrNull())
        }
    }

    @Test
    fun `it should save a oauth user`() {
        runMongoDbRepositoryTest<OAuthUserDocument>(OAuthUserDocument.Companion, save = false) {
            val saveResult = repository.save(it)
            assertIs<Unit>(saveResult.getOrNull(), saveResult.leftOrNull()?.message)

            val result = repository.find(User.UserId(it.userId))
            assertEquals(it, result.getOrNull())
        }
    }

    @Test
    fun `it should update existing oauth user`() {
        runMongoDbRepositoryTest<OAuthUserDocument>(OAuthUserDocument.Companion, save = false) {
            repository.save(it).let { result ->
                assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
            }

            val updated = it.copy(name = "new name")

            repository.save(updated).let { result ->
                assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
            }

            val result = repository.find(User.UserId(it.userId))
            assertEquals(updated, result.getOrNull())
        }
    }

}
