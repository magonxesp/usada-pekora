package com.usadapekora.auth.application.oauth

import arrow.core.left
import arrow.core.right
import com.usadapekora.auth.domain.AuthorizationGrantMother
import com.usadapekora.auth.domain.Random
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationGrantCodeCreator
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationProvider
import com.usadapekora.auth.domain.oauth.OAuthProvider
import com.usadapekora.auth.domain.oauth.OAuthProviderFactory
import com.usadapekora.auth.domain.shared.AuthorizationGrantRepository
import com.usadapekora.shared.domain.OAuthUserMother
import com.usadapekora.shared.domain.auth.AuthorizationGrantedEvent
import com.usadapekora.shared.domain.auth.OAuthUserRepository
import com.usadapekora.shared.domain.bus.event.EventBus
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.domain.user.UserException
import com.usadapekora.shared.domain.user.UserRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class OAuthAuthorizationProviderAuthorizationHandlerTest {

    private val factory = mockk<OAuthProviderFactory>()
    private val provider = mockk<OAuthAuthorizationProvider>()
    private val userRepository = mockk<UserRepository>(relaxUnitFun = true)
    private val oAuthUserRepository = mockk<OAuthUserRepository>(relaxUnitFun = true)
    private val authorizationCodeRepository = mockk<AuthorizationGrantRepository>(relaxUnitFun = true)
    private val authorizationCodeCreator = mockk<OAuthAuthorizationGrantCodeCreator>()
    private val clock = mockk<Clock>()
    private val eventBus = mockk<EventBus>(relaxUnitFun = true)
    private val handler = OAuthAuthorizationProviderAuthorizationHandler(
        providerFactory = factory,
        userRepository = userRepository,
        oAuthUserRepository = oAuthUserRepository,
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
        every { userRepository.findByDiscordId(User.UserProviderId(oAuthUser.id)) } returns UserException.NotFound().left()
        every { oAuthUserRepository.save(oAuthUser) } returns Unit.right()
        every { clock.now() } returns issuedAt

        val newUser = User.fromPrimitives(
            id = oAuthUser.userId,
            name = oAuthUser.name ?: "unnamed",
            avatar = oAuthUser.avatar,
            providerId = oAuthUser.id,
            provider = oAuthUser.provider
        )

        val grantCode = AuthorizationGrantMother.create(user = newUser.id.value, issuedAt = issuedAt)
        val event = AuthorizationGrantedEvent(userId = oAuthUser.userId, occurredOn = issuedAt.toString())

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
        verify { oAuthUserRepository.save(oAuthUser.copy(userId = newUser.id.value)) }
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
            id = oAuthUser.userId,
            name = oAuthUser.name ?: "unnamed",
            avatar = oAuthUser.avatar,
            providerId = oAuthUser.id,
            provider = oAuthUser.provider
        )

        every { factory.getInstance(OAuthProvider.DISCORD) } returns provider.right()
        coEvery { provider.handleCallback(code) } returns oAuthUser.right()
        every { userRepository.findByDiscordId(User.UserProviderId(oAuthUser.id)) } returns user.right()
        every { oAuthUserRepository.save(oAuthUser) } returns Unit.right()
        every { clock.now() } returns issuedAt

        val grantCode = AuthorizationGrantMother.create(user = user.id.value, issuedAt = issuedAt)
        val event = AuthorizationGrantedEvent(userId = oAuthUser.userId, occurredOn = issuedAt.toString())

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
        verify { oAuthUserRepository.save(oAuthUser) }
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
    fun `it should save the provided user if it is not saved`() = runBlocking {
        val oAuthUser = OAuthUserMother.create()
        val code = Random.instance().code.toString()
        val issuedAt = Clock.System.now()
        val user = User.fromPrimitives(
            id = UUID.randomUUID().toString(),
            name = oAuthUser.name ?: "unnamed",
            avatar = oAuthUser.avatar,
            providerId = oAuthUser.id,
            provider = oAuthUser.provider
        )
        val oAuthUserToSave = oAuthUser.copy(userId = user.id.value)

        every { factory.getInstance(OAuthProvider.DISCORD) } returns provider.right()
        coEvery { provider.handleCallback(code) } returns oAuthUser.right()
        every { userRepository.findByDiscordId(User.UserProviderId(oAuthUser.id)) } returns user.right()
        every { oAuthUserRepository.save(oAuthUserToSave) } returns Unit.right()
        every { clock.now() } returns issuedAt

        val grantCode = AuthorizationGrantMother.create(user = user.id.value, issuedAt = issuedAt)
        val event = AuthorizationGrantedEvent(userId = oAuthUserToSave.userId, occurredOn = issuedAt.toString())

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
        verify { oAuthUserRepository.save(oAuthUserToSave) }
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
