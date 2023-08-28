package com.usadapekora.auth.infrastructure.jwt.auth0

import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertNotNull

class Auth0JwkIssuerTest {

    @Test
    fun `it should issue a jwk by the generated certificates`() {
        val issuer = Auth0JwkIssuer(Clock.System)
        val result = issuer.issue()

        assertNotNull(result.getOrNull(), result.leftOrNull()?.message)
    }

}
