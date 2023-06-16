package com.usadapekora.bot.application.guild

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.guild.find.GuildPreferencesFinder
import com.usadapekora.bot.domain.guild.GuildPreferencesException
import com.usadapekora.bot.domain.guild.GuildPreferencesMother
import com.usadapekora.bot.domain.guild.GuildPreferencesRepository
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GuildPreferencesFinderTest {

    @Test
    fun `should find by guild id`() {
        val preferences = GuildPreferencesMother.create()
        val repository = mockk<GuildPreferencesRepository>()
        val finder = GuildPreferencesFinder(repository)

        every { repository.findByGuildId(preferences.guildId) } returns preferences.right()

        val found = finder.find(preferences.guildId)

        assertEquals(preferences, found.getOrNull())
    }

    @Test
    fun `should not find by guild id`() {
        val preferences = GuildPreferencesMother.create()
        val repository = mockk<GuildPreferencesRepository>()
        val finder = GuildPreferencesFinder(repository)

        every { repository.findByGuildId(preferences.guildId) } returns GuildPreferencesException.NotFound().left()

        val result = finder.find(preferences.guildId)
        assertTrue(result.leftOrNull() is GuildPreferencesException.NotFound)
    }

}
