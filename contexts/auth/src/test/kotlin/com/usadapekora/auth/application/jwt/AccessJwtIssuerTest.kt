package com.usadapekora.auth.application.jwt

import arrow.core.right
import com.usadapekora.auth.domain.AuthorizationGrantMother
import com.usadapekora.auth.domain.JwtMother
import com.usadapekora.auth.domain.jwt.JwtIssuer
import com.usadapekora.auth.domain.shared.AuthorizationGrantRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertEquals

class AccessJwtIssuerTest {

    @Test
    fun `it should issue a jwt for the granted user`() {
        val authorizationGrantRepository = mockk<AuthorizationGrantRepository>()
        val jwtIssuer = mockk<JwtIssuer>()
        val clock = mockk<Clock>()
        val accessIssuer = AccessJwtIssuer(authorizationGrantRepository, jwtIssuer, clock)
        val authorizationGrant = AuthorizationGrantMother.create()
        val jwtToken = JwtMother.create()

        every { authorizationGrantRepository.find(authorizationGrant.code) } returns authorizationGrant.right()
        every { jwtIssuer.issue(authorizationGrant, jwtToken.expiresAt.value) } returns jwtToken.right()

        val result = accessIssuer.issue(authorizationGrant.code.value)

        verify { authorizationGrantRepository.find(authorizationGrant.code) }
        verify { jwtIssuer.issue(authorizationGrant, jwtToken.expiresAt.value) }

        assertEquals(jwtToken, result.getOrNull())
    }

}
