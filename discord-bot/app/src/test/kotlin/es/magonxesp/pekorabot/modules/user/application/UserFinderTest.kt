package es.magonxesp.pekorabot.modules.user.application

import es.magonxesp.pekorabot.modules.user.domain.UserException
import es.magonxesp.pekorabot.modules.user.domain.UserMother
import es.magonxesp.pekorabot.modules.user.domain.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class UserFinderTest {

    @Test
    fun `should find user by discord id`() {
        val user = UserMother.create()
        val repository = mockk<UserRepository>()
        val finder = UserFinder(repository)

        every { repository.findByDiscordId(user.discordId) } returns user

        val existing = finder.findByDiscordId(user.discordId)

        assertEquals(user, existing)
    }

    @Test
    fun `should not find user by discord id`() {
        val user = UserMother.create()
        val repository = mockk<UserRepository>()
        val finder = UserFinder(repository)

        every { repository.findByDiscordId(user.discordId) } throws UserException.NotFound()

        assertThrows<UserException.NotFound> {
            finder.findByDiscordId(user.discordId)
        }
    }

}
