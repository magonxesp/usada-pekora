package com.usadapekora.auth.application

import com.usadapekora.auth.domain.OAuthProviderError
import kotlin.test.Test
import kotlin.test.assertIs

class AuthorizationUrlProviderTest {

    @Test
    fun `it should return the discord provider authorize url`() {
        val urlProvider = AuthorizationUrlProvider()
        val result = urlProvider.getUrl("discord")

        assertIs<String>(result.getOrNull())
    }

    @Test
    fun `it should not return unknown provider authorize url`() {
        val urlProvider = AuthorizationUrlProvider()
        val result = urlProvider.getUrl("unknown")

        assertIs<OAuthProviderError.NotAvailable>(result.leftOrNull())
    }

}
