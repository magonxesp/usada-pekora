package com.usadapekora.auth.application.oauth

import arrow.core.left
import arrow.core.right
import com.usadapekora.auth.domain.OAuthAuthorizationGrantMother
import com.usadapekora.auth.domain.OAuthUserMother
import com.usadapekora.auth.domain.Random
import com.usadapekora.auth.domain.oauth.*
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.domain.user.UserException
import com.usadapekora.shared.domain.user.UserRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertEquals

class OAuthAuthorizationProviderAuthorizationHandlerTest {

    @Test
    fun `it should handle the code and create the user when it does not exists and then generate the authorization code`() = runBlocking {
        val factory = mockk<OAuthProviderFactory>()
        val provider = mockk<OAuthAuthorizationProvider>()
        val userRepository = mockk<UserRepository>(relaxUnitFun = true)
        val authorizationCodeRepository = mockk<OAuthAuthorizationGrantRepository>(relaxUnitFun = true)
        val authorizationCodeCreator = mockk<OAuthAuthorizationGrantCodeCreator>()
        val clock = mockk<Clock>()
        val handler = OAuthAuthorizationProviderAuthorizationHandler(
            providerFactory = factory,
            userRepository = userRepository,
            grantCodeRepository = authorizationCodeRepository,
            grantCodeCreator = authorizationCodeCreator,
            clock = clock
        )

        val oAuthUser = OAuthUserMother.create()
        val code = Random.instance().code.toString()
        val issuedAt = Clock.System.now()

        every { factory.getInstance(OAuthProvider.DISCORD) } returns provider.right()
        coEvery { provider.handleCallback(code) } returns oAuthUser.right()
        every { userRepository.findByDiscordId(User.DiscordUserId(oAuthUser.id)) } returns UserException.NotFound().left()
        every { clock.now() } returns issuedAt

        val newUser = User.fromPrimitives(
            id = oAuthUser.nextDomainUserId,
            name = oAuthUser.name ?: "unnamed",
            avatar = oAuthUser.avatar,
            discordId = oAuthUser.id
        )

        val grantCode = OAuthAuthorizationGrantMother.create(user = newUser.id.value, issuedAt = issuedAt)

        every { authorizationCodeCreator.fromOAuthUser(oAuthUser, newUser.id) } returns grantCode.code

        val result = handler.handle("discord", code)

        verify { userRepository.save(newUser) }
        verify { authorizationCodeRepository.save(grantCode) }

        assertEquals(grantCode.code, result.getOrNull())
    }

    @Test
    fun `it should handle the code and generate the authorization code`() = runBlocking {
        val factory = mockk<OAuthProviderFactory>()
        val provider = mockk<OAuthAuthorizationProvider>()
        val userRepository = mockk<UserRepository>()
        val authorizationCodeRepository = mockk<OAuthAuthorizationGrantRepository>(relaxUnitFun = true)
        val authorizationCodeCreator = mockk<OAuthAuthorizationGrantCodeCreator>()
        val clock = mockk<Clock>()
        val handler = OAuthAuthorizationProviderAuthorizationHandler(
            providerFactory = factory,
            userRepository = userRepository,
            grantCodeRepository = authorizationCodeRepository,
            grantCodeCreator = authorizationCodeCreator,
            clock = clock
        )

        val oAuthUser = OAuthUserMother.create()
        val code = Random.instance().code.toString()
        val issuedAt = Clock.System.now()
        val user = User.fromPrimitives(
            id = oAuthUser.nextDomainUserId,
            name = oAuthUser.name ?: "unnamed",
            avatar = oAuthUser.avatar,
            discordId = oAuthUser.id
        )

        every { factory.getInstance(OAuthProvider.DISCORD) } returns provider.right()
        coEvery { provider.handleCallback(code) } returns oAuthUser.right()
        every { userRepository.findByDiscordId(User.DiscordUserId(oAuthUser.id)) } returns user.right()
        every { clock.now() } returns issuedAt

        val grantCode = OAuthAuthorizationGrantMother.create(user = user.id.value, issuedAt = issuedAt)

        every { authorizationCodeCreator.fromOAuthUser(oAuthUser, user.id) } returns grantCode.code

        val result = handler.handle("discord", code)

        verify(inverse = true) { userRepository.save(user) }
        verify { authorizationCodeRepository.save(grantCode) }

        assertEquals(grantCode.code, result.getOrNull())
    }

}
