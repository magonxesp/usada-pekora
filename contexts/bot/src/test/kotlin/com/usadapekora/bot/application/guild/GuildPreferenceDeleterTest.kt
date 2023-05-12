package com.usadapekora.bot.application.guild

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.bot.domain.guild.GuildPreferencesException
import com.usadapekora.bot.domain.GuildPreferencesMother
import com.usadapekora.bot.domain.guild.GuildPreferencesRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class GuildPreferenceDeleterTest {

    @Test
    fun `should delete existing preference`() {
        val preferences = GuildPreferencesMother.create()
        val repository = mockk<GuildPreferencesRepository>(relaxed = true)
        val deleter = GuildPreferenceDeleter(repository)

        every { repository.findByGuildId(preferences.guildId) } returns preferences.right()

        deleter.delete(preferences.guildId, GuildPreferences.GuildPreference.FeedChannelId)

        verify { repository.findByGuildId(preferences.guildId) }
        verify { repository.save(preferences) }
    }

    @Test
    fun `should not delete missing preference`() {
        val preferences = GuildPreferencesMother.create()
        val repository = mockk<GuildPreferencesRepository>(relaxed = true)
        val deleter = GuildPreferenceDeleter(repository)

        every { repository.findByGuildId(preferences.guildId) } returns GuildPreferencesException.NotFound().left()

        deleter.delete(preferences.guildId, GuildPreferences.GuildPreference.FeedChannelId)

        verify { repository.findByGuildId(preferences.guildId) }
        verify(inverse = true) { repository.save(preferences) }
    }

}
