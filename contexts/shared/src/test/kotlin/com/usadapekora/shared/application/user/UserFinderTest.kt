package com.usadapekora.shared.application.user

import arrow.core.left
import arrow.core.right
import com.usadapekora.shared.domain.user.UserException
import com.usadapekora.shared.domain.UserMother
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

        every { repository.findByDiscordId(user.discordId) } returns user.right()

        val existing = finder.findByDiscordId(user.discordId)

        assertEquals(user, existing.getOrNull())
    }

    @Test
    fun `should not find user by discord id`() {
        val user = UserMother.create()
        val repository = mockk<UserRepository>()
        val finder = UserFinder(repository)

        every { repository.findByDiscordId(user.discordId) } returns UserException.NotFound().left()

        val result = finder.findByDiscordId(user.discordId)
        assertTrue(result.leftOrNull() is UserException.NotFound)
    }

}