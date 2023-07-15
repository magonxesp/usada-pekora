package com.usadapekora.shared.application.user

import arrow.core.left
import arrow.core.right
import com.usadapekora.shared.application.user.find.UserFinder
import com.usadapekora.shared.application.user.find.UserResponse
import com.usadapekora.shared.domain.UserMother
import com.usadapekora.shared.domain.user.UserException
import com.usadapekora.shared.domain.user.UserRepository
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class UserFinderTest {

    @Test
    fun `should find user by id`() {
        val user = UserMother.create()
        val repository = mockk<UserRepository>()
        val finder = UserFinder(repository)

        every { repository.find(user.id) } returns user.right()

        val existing = finder.find(user.id)

        assertEquals(UserResponse.fromEntity(user), existing.getOrNull())
    }

    @Test
    fun `should not find user by id`() {
        val user = UserMother.create()
        val repository = mockk<UserRepository>()
        val finder = UserFinder(repository)

        every { repository.find(user.id) } returns UserException.NotFound().left()

        val result = finder.find(user.id)

        assertIs<UserException.NotFound>(result.leftOrNull())
    }

    @Test
    fun `should find user by provider id`() {
        val user = UserMother.create()
        val repository = mockk<UserRepository>()
        val finder = UserFinder(repository)

        every { repository.findByProviderId(user.providerId) } returns user.right()

        val existing = finder.findByProviderId(user.providerId)

        assertEquals(UserResponse.fromEntity(user), existing.getOrNull())
    }

    @Test
    fun `should not find user by provider id`() {
        val user = UserMother.create()
        val repository = mockk<UserRepository>()
        val finder = UserFinder(repository)

        every { repository.findByProviderId(user.providerId) } returns UserException.NotFound().left()

        val result = finder.findByProviderId(user.providerId)
        assertTrue(result.leftOrNull() is UserException.NotFound)
    }

}
