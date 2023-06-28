package com.usadapekora.bot.application.guild

import com.usadapekora.bot.application.guild.create.CreateGuildsFromProviderOnAuthorizationGranted
import com.usadapekora.bot.application.guild.create.GuildCreator
import com.usadapekora.bot.application.guild.create.GuildMemberCreator
import com.usadapekora.bot.application.guild.update.ProvidedGuildUpdater
import com.usadapekora.bot.domain.guild.GuildMother
import com.usadapekora.bot.domain.guild.GuildProviderRepositoryFactory
import com.usadapekora.shared.domain.UserMother
import com.usadapekora.shared.domain.auth.OAuthUserRepository
import io.mockk.clearAllMocks
import io.mockk.mockk
import kotlin.test.BeforeTest
import kotlin.test.Test

class CreateGuildsFromProviderOnAuthorizationGrantedTest {

    private val oAuthUserRepository = mockk<OAuthUserRepository>()
    private val guildProviderRepositoryFactory = mockk<GuildProviderRepositoryFactory>()
    private val guildCreator = mockk<GuildCreator>()
    private val guildMemberCreator = mockk<GuildMemberCreator>()
    private val providedGuildUpdater = mockk<ProvidedGuildUpdater>()
    private val eventSubscriber = CreateGuildsFromProviderOnAuthorizationGranted(
        oAuthUserRepository, guildProviderRepositoryFactory, guildCreator, guildMemberCreator, providedGuildUpdater
    )

    @BeforeTest
    fun beforeTest() = clearAllMocks()

    @Test
    fun `it should save guilds from provider`() {
        val user = UserMother.create()
        val guilds = (1..5).map { GuildMother.create() }
        // TODO
    }

}
