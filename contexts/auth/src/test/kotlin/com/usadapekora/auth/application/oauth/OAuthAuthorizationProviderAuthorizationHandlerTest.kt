package com.usadapekora.auth.application.oauth

import arrow.core.left
import arrow.core.right
import com.usadapekora.auth.domain.AuthorizationGrantMother
import com.usadapekora.auth.domain.OAuthUserMother
import com.usadapekora.auth.domain.Random
import com.usadapekora.auth.domain.oauth.*
import com.usadapekora.auth.domain.shared.AuthorizationGrantRepository
import com.usadapekora.shared.domain.auth.AuthorizationGrantedEvent
import com.usadapekora.shared.domain.bus.event.EventBus
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.domain.user.UserException
import com.usadapekora.shared.domain.user.UserRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class OAuthAuthorizationProviderAuthorizationHandlerTest {

    private val factory = mockk<OAuthProviderFactory>()
    private val provider = mockk<OAuthAuthorizationProvider>()
    private val userRepository = mockk<UserRepository>(relaxUnitFun = true)
    private val authorizationCodeRepository = mockk<AuthorizationGrantRepository>(relaxUnitFun = true)
    private val authorizationCodeCreator = mockk<OAuthAuthorizationGrantCodeCreator>()
    private val clock = mockk<Clock>()
    private val eventBus = mockk<EventBus>(relaxUnitFun = true)
    private val handler = OAuthAuthorizationProviderAuthorizationHandler(
        providerFactory = factory,
        userRepository = userRepository,
        grantCodeRepository = authorizationCodeRepository,
        grantCodeCreator = authorizationCodeCreator,
        clock = clock,
        eventBus = eventBus
    )

    @BeforeTest
    fun beforeTest() {
        clearAllMocks()
    }

    @Test
    fun `it should handle the code and create the user when it does not exists and then generate the authorization code`() = runBlocking {
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

        val grantCode = AuthorizationGrantMother.create(user = newUser.id.value, issuedAt = issuedAt)
        val event = AuthorizationGrantedEvent(userId = oAuthUser.nextDomainUserId, occurredOn = issuedAt.toString())

        every { authorizationCodeCreator.fromOAuthUser(oAuthUser, newUser.id) } returns grantCode.code
        every {
            eventBus.dispatch(
                match {
                    it.occurredOn == event.occurredOn
                        && it.name == event.name
                        && (it as AuthorizationGrantedEvent).userId == event.userId
                }
            )
        } returns Unit.right()

        val result = handler.handle("discord", code)

        verify { userRepository.save(newUser) }
        verify { authorizationCodeRepository.save(grantCode) }
        verify {
            eventBus.dispatch(
                match {
                    it.occurredOn == event.occurredOn
                        && it.name == event.name
                        && (it as AuthorizationGrantedEvent).userId == event.userId
                }
            )
        }

        assertEquals(grantCode.code, result.getOrNull())
    }

    @Test
    fun `it should handle the code and generate the authorization code`() = runBlocking {
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

        val grantCode = AuthorizationGrantMother.create(user = user.id.value, issuedAt = issuedAt)
        val event = AuthorizationGrantedEvent(userId = oAuthUser.nextDomainUserId, occurredOn = issuedAt.toString())

        every { authorizationCodeCreator.fromOAuthUser(oAuthUser, user.id) } returns grantCode.code
        every {
            eventBus.dispatch(
                match {
                    it.occurredOn == event.occurredOn
                        && it.name == event.name
                        && (it as AuthorizationGrantedEvent).userId == event.userId
                }
            )
        } returns Unit.right()

        val result = handler.handle("discord", code)

        verify(inverse = true) { userRepository.save(user) }
        verify { authorizationCodeRepository.save(grantCode) }
        verify {
            eventBus.dispatch(
                match {
                    it.occurredOn == event.occurredOn
                        && it.name == event.name
                        && (it as AuthorizationGrantedEvent).userId == event.userId
                }
            )
        }

        assertEquals(grantCode.code, result.getOrNull())
    }

}
