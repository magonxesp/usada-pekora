package com.usadapekora.context.application.guild

import com.usadapekora.context.domain.guild.GuildPreferences
import com.usadapekora.context.domain.guild.GuildPreferencesException
import com.usadapekora.context.domain.GuildPreferencesMother
import com.usadapekora.context.domain.guild.GuildPreferencesRepository
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

        every { repository.findByGuildId(preferences.guildId) } returns preferences

        deleter.delete(preferences.guildId, GuildPreferences.GuildPreference.FeedChannelId)

        verify { repository.findByGuildId(preferences.guildId) }
        verify { repository.save(preferences) }
    }

    @Test
    fun `should not delete missing preference`() {
        val preferences = GuildPreferencesMother.create()
        val repository = mockk<GuildPreferencesRepository>(relaxed = true)
        val deleter = GuildPreferenceDeleter(repository)

        every { repository.findByGuildId(preferences.guildId) } throws GuildPreferencesException.NotFound()

        deleter.delete(preferences.guildId, GuildPreferences.GuildPreference.FeedChannelId)

        verify { repository.findByGuildId(preferences.guildId) }
        verify(inverse = true) { repository.save(preferences) }
    }

}
