package com.usadapekora.shared.application.user

import arrow.core.left
import arrow.core.right
import com.usadapekora.shared.application.user.find.UserFinder
import com.usadapekora.shared.domain.UserMother
import com.usadapekora.shared.domain.user.UserException
import com.usadapekora.shared.domain.user.UserRepository
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserFinderTest {

    @Test
    fun `should find user by discord id`() {
        val user = UserMother.create()
        val repository = mockk<UserRepository>()
        val finder = UserFinder(repository)

        every { repository.findByDiscordId(user.providerId) } returns user.right()

        val existing = finder.findByDiscordId(user.providerId)

        assertEquals(user, existing.getOrNull())
    }

    @Test
    fun `should not find user by discord id`() {
        val user = UserMother.create()
        val repository = mockk<UserRepository>()
        val finder = UserFinder(repository)

        every { repository.findByDiscordId(user.providerId) } returns UserException.NotFound().left()

        val result = finder.findByDiscordId(user.providerId)
        assertTrue(result.leftOrNull() is UserException.NotFound)
    }

}
