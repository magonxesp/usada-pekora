package com.usadapekora.bot.application.guild

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.guild.create.GuildPreferenceCreator
import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.bot.domain.guild.GuildPreferencesException
import com.usadapekora.bot.domain.guild.GuildPreferencesMother
import com.usadapekora.bot.domain.guild.GuildPreferencesRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*
import kotlin.test.Test

class GuildPreferenceCreatorTest {

    @Test
    fun `should create a guild preference does not exists`() {
        val preferences = GuildPreferencesMother.create()
        preferences.preferences[GuildPreferences.GuildPreference.FeedChannelId] = Random().nextLong().toString()

        val repository = mockk<GuildPreferencesRepository>(relaxed = true)
        val creator = GuildPreferenceCreator(repository)

        every { repository.findByGuildId(preferences.guildId) } returns GuildPreferencesException.NotFound().left()

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

        every { repository.findByGuildId(preferences.guildId) } returns preferences.right()

        creator.create(
            preferences.guildId,
            GuildPreferences.GuildPreference.FeedChannelId,
            preferences.preferences[GuildPreferences.GuildPreference.FeedChannelId]!!
        )

        verify { repository.findByGuildId(preferences.guildId) }
        verify { repository.save(preferences) }
    }

}
