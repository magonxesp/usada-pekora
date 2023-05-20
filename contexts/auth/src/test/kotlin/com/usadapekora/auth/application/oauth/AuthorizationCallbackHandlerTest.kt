package com.usadapekora.auth.application.oauth

import arrow.core.right
import com.usadapekora.auth.domain.OAuthUserMother
import com.usadapekora.auth.domain.Random
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationProvider
import com.usadapekora.auth.domain.oauth.OAuthProvider
import com.usadapekora.auth.domain.oauth.OAuthProviderFactory
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class AuthorizationCallbackHandlerTest {

    @Test
    fun `it should handle the code and `() = runBlocking {
        val factory = mockk<OAuthProviderFactory>()
        val provider = mockk<OAuthAuthorizationProvider>()
        val handler = AuthorizationCallbackHandler(factory)
        val oAuthUser = OAuthUserMother.create()
        val code = Random.instance().code.toString()

        every { factory.getInstance(OAuthProvider.DISCORD) } returns provider.right()
        coEvery { provider.handleCallback(code) } returns oAuthUser.right()

        val result = handler.handle("discord", code)

        assertTrue(result.isRight())
    }

}
