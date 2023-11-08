package com.usadapekora.auth.application.oauth

import arrow.core.right
import com.usadapekora.auth.domain.Random
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationProvider
import com.usadapekora.auth.domain.oauth.OAuthProvider
import com.usadapekora.auth.domain.oauth.OAuthProviderException
import com.usadapekora.auth.domain.oauth.OAuthProviderFactory
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertIs

class OAuthAuthorizationProviderAuthorizeUrlFactoryTest {

    @Test
    fun `it should return the discord provider authorize url`() {
        val factory = mockk<OAuthProviderFactory>()
        val provider = mockk<OAuthAuthorizationProvider>()
        val urlProvider = OAuthAuthorizationProviderAuthorizeUrlFactory(factory)

        every { factory.getInstance(OAuthProvider.DISCORD) } returns provider.right()
        every { provider.authorizeUrl() } returns Random.instance().internet.domain()

        val result = urlProvider.getUrl("discord")

        assertIs<String>(result.getOrNull())
    }

    @Test
    fun `it should not return unknown provider authorize url`() {
        val factory = mockk<OAuthProviderFactory>()
        val provider = mockk<OAuthAuthorizationProvider>()
        val urlProvider = OAuthAuthorizationProviderAuthorizeUrlFactory(factory)

        every { factory.getInstance(OAuthProvider.DISCORD) } returns provider.right()
        every { provider.authorizeUrl() } returns Random.instance().internet.domain()

        val result = urlProvider.getUrl("unknown")

        assertIs<OAuthProviderException.NotAvailable>(result.leftOrNull())
    }

}
