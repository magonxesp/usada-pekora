package es.magonxesp.pekorabot.modules.guild.application

import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferencesException
import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferencesMother
import es.magonxesp.pekorabot.modules.guild.domain.GuildPreferencesRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class GuildPreferencesFinderTest {

    @Test
    fun `should find by guild id`() {
        val preferences = GuildPreferencesMother.create()
        val repository = mockk<GuildPreferencesRepository>()
        val finder = GuildPreferencesFinder(repository)

        every { repository.findByGuildId(preferences.guildId) } returns preferences

        val found = finder.find(preferences.guildId)

        assertEquals(preferences, found)
    }

    @Test
    fun `should not find by guild id`() {
        val preferences = GuildPreferencesMother.create()
        val repository = mockk<GuildPreferencesRepository>()
        val finder = GuildPreferencesFinder(repository)

        every { repository.findByGuildId(preferences.guildId) } throws GuildPreferencesException.NotFound()

        assertThrows<GuildPreferencesException.NotFound> {
            finder.find(preferences.guildId)
        }
    }

}
