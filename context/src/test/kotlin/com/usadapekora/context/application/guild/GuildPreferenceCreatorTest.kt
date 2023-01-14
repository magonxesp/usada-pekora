package com.usadapekora.context.application.guild

import com.usadapekora.context.domain.guild.GuildPreferences
import com.usadapekora.context.domain.guild.GuildPreferencesException
import com.usadapekora.context.domain.GuildPreferencesMother
import com.usadapekora.context.domain.guild.GuildPreferencesRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.Random
import kotlin.test.Test

class GuildPreferenceCreatorTest {

    @Test
    fun `should create a guild preference does not exists`() {
        val preferences = GuildPreferencesMother.create()
        preferences.preferences[GuildPreferences.GuildPreference.FeedChannelId] = Random().nextLong().toString()

        val repository = mockk<GuildPreferencesRepository>(relaxed = true)
        val creator = GuildPreferenceCreator(repository)

        every { repository.findByGuildId(preferences.guildId) } throws GuildPreferencesException.NotFound()

        creator.create(
            preferences.guildId,
            GuildPreferences.GuildPreference.FeedChannelId,
            preferences.preferences[GuildPreferences.GuildPreference.FeedChannelId]!!
        )

        verify { repository.findByGuildId(preferences.guildId) }
        verify { repository.save(preferences) }
    }

    @Test
    fun `should create a guild preference on existing guild preferences`() {
        val preferences = GuildPreferencesMother.create()
        preferences.preferences[GuildPreferences.GuildPreference.FeedChannelId] = Random().nextLong().toString()

        val repository = mockk<GuildPreferencesRepository>(relaxed = true)
        val creator = GuildPreferenceCreator(repository)

        every { repository.findByGuildId(preferences.guildId) } returns preferences

        creator.create(
            preferences.guildId,
            GuildPreferences.GuildPreference.FeedChannelId,
            preferences.preferences[GuildPreferences.GuildPreference.FeedChannelId]!!
        )

        verify { repository.findByGuildId(preferences.guildId) }
        verify { repository.save(preferences) }
    }

}
