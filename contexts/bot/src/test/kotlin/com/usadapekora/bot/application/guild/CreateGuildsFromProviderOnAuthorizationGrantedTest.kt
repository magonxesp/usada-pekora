package com.usadapekora.bot.application.guild

import arrow.core.right
import com.usadapekora.bot.application.guild.create.*
import com.usadapekora.bot.application.guild.update.ProvidedGuildUpdateRequest
import com.usadapekora.bot.application.guild.update.ProvidedGuildUpdater
import com.usadapekora.bot.domain.guild.GuildMother
import com.usadapekora.bot.domain.guild.GuildProvider
import com.usadapekora.bot.domain.guild.GuildProviderRepository
import com.usadapekora.bot.domain.guild.GuildProviderRepositoryFactory
import com.usadapekora.shared.domain.OAuthUserMother
import com.usadapekora.shared.domain.UserMother
import com.usadapekora.shared.domain.auth.AuthorizationGrantedEvent
import com.usadapekora.shared.domain.auth.OAuthUserRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs

class CreateGuildsFromProviderOnAuthorizationGrantedTest {

    private val oAuthUserRepository = mockk<OAuthUserRepository>()
    private val guildProviderRepositoryFactory = mockk<GuildProviderRepositoryFactory>()
    private val guildProviderRepository = mockk<GuildProviderRepository>()
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
        val oAuthUser = OAuthUserMother.create()
        val user = UserMother.create(
            id = oAuthUser.userId,
            name = oAuthUser.name,
            avatar = oAuthUser.avatar,
            discordId = oAuthUser.id
        )
        val guilds = (1..5).map { GuildMother.create() }
        val event = AuthorizationGrantedEvent(
            userId = oAuthUser.userId
        )

        every { oAuthUserRepository.find(user.id) } returns oAuthUser.right()
        every {
            guildProviderRepositoryFactory.getInstance(GuildProvider.fromValue(oAuthUser.provider), oAuthUser.token)
        } returns guildProviderRepository.right()
        every { guildProviderRepository.findAll(user.id) } returns guilds.toTypedArray()

        for (guild in guilds) {
            val createRequest = GuildCreateRequest(
                id = guild.id.value,
                providerId = guild.providerId.value,
                provider = guild.provider.value,
                name = guild.name.value,
                iconUrl = guild.iconUrl.value
            )

            val createMemberRequest = GuildMemberCreateRequest(
                userId = user.id.value,
                guildId = guild.id.value
            )

            every { guildCreator.create(createRequest) } returns Unit.right()
            every { guildMemberCreator.create(createMemberRequest) } returns Unit.right()
            every {
                providedGuildUpdater.update(
                    ProvidedGuildUpdateRequest(
                        provider = guild.provider.value,
                        providerId = guild.providerId.value,
                        values = ProvidedGuildUpdateRequest.NewValues(
                            name = guild.name.value,
                            iconUrl = guild.iconUrl.value
                        )
                    )
                )
            } returns Unit.right()
        }

        val result = eventSubscriber.handle(event)
        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
    }

}
