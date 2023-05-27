package com.usadapekora.auth.infrastructure.jwt.auth0

import com.usadapekora.auth.domain.AuthorizationGrantMother
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertNotNull

class Auth0JwtIssuerTest {

    @Test
    fun `it should issue a jwt by providing an authorization grant`() {
        val authorizationGrant = AuthorizationGrantMother.create()
        val now = Clock.System.now()
        val clock = mockk<Clock>()
        val jwtIssuer = Auth0JwtIssuer(clock)

        every { clock.now() } returns now

        val result = jwtIssuer.issue(authorizationGrant, authorizationGrant.expiresAt.seconds)

        assertNotNull(result.getOrNull())
    }

}
