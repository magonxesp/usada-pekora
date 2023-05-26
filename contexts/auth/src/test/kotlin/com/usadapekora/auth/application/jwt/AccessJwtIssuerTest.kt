package com.usadapekora.auth.application.jwt

import arrow.core.right
import com.usadapekora.auth.domain.AuthorizationGrantMother
import com.usadapekora.auth.domain.JwtTokenMother
import com.usadapekora.auth.domain.jwt.JwtIssuer
import com.usadapekora.auth.domain.jwt.JwtToken
import com.usadapekora.auth.domain.shared.AuthorizationGrantRepository
import io.mockk.every
import io.mockk.mockk
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
        val jwtToken = JwtTokenMother.create()

        every { authorizationGrantRepository.find(authorizationGrant.code) } returns authorizationGrant.right()
        every { jwtIssuer.issue(authorizationGrant.code, jwtToken.expiresAt.value) } returns jwtToken.right()

        val result = accessIssuer.issue(authorizationGrant.code.value)

        assertEquals(jwtToken, result.getOrNull())
    }

}
