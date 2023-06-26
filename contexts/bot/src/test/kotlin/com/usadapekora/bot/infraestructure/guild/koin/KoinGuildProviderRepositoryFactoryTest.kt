package com.usadapekora.bot.infraestructure.guild.koin

import com.usadapekora.bot.DependencyInjectionEnabledTest
import com.usadapekora.bot.domain.guild.GuildProvider
import com.usadapekora.bot.infraestructure.guild.persistence.discord.DiscordGuildProviderRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs

class KoinGuildProviderRepositoryFactoryTest : DependencyInjectionEnabledTest() {

    @BeforeTest
    fun setup() = setupTestModules()

    @Test
    fun `it should return an implementation of GuildProviderRepository by providing a guild provider`() {
        val factory = KoinGuildProviderRepositoryFactory()
        val result = factory.getInstance(GuildProvider.DISCORD, "exampletoken")
        assertIs<DiscordGuildProviderRepository>(result.getOrNull())
    }

}
