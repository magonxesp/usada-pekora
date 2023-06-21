package com.usadapekora.auth.application.jwt

import arrow.core.right
import com.usadapekora.auth.domain.AuthorizationGrantMother
import com.usadapekora.auth.domain.JwtMother
import com.usadapekora.auth.domain.jwt.Jwt
import com.usadapekora.auth.domain.jwt.JwtIssuer
import com.usadapekora.auth.domain.shared.AuthorizationGrantRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus
import kotlin.test.Test
import kotlin.test.assertEquals

class AccessJwtIssuerTest {

    @Test
    fun `it should issue a jwt for the granted user`() {
        val authorizationGrantRepository = mockk<AuthorizationGrantRepository>()
        val jwtIssuer = mockk<JwtIssuer>()
        val accessIssuer = AccessJwtIssuer(authorizationGrantRepository, jwtIssuer)
        val authorizationGrant = AuthorizationGrantMother.create()
        val expirationTime = Jwt.JwtExpiresAt.DEFAULT_EXPIRATION_TIME
        val jwtToken = JwtMother.create(expiresAt = Clock.System.now().plus(expirationTime, DateTimeUnit.SECOND))

        every { authorizationGrantRepository.find(authorizationGrant.code) } returns authorizationGrant.right()
        every { jwtIssuer.issue(authorizationGrant, expirationTime) } returns jwtToken.right()

        val result = accessIssuer.issue(authorizationGrant.code.value)
        val expected = AccessJwtIssuerResponse(jwtToken.token.value, jwtToken.expiresAt.value.toEpochMilliseconds())

        verify { authorizationGrantRepository.find(authorizationGrant.code) }
        verify { jwtIssuer.issue(authorizationGrant, expirationTime) }

        assertEquals(expected, result.getOrNull())
    }

}
