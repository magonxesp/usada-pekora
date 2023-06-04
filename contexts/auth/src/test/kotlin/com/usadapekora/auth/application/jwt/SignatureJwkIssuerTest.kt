package com.usadapekora.auth.application.jwt

import arrow.core.left
import arrow.core.right
import com.usadapekora.auth.domain.jwt.JwkError
import com.usadapekora.auth.domain.jwt.JwkIssuer
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class SignatureJwkIssuerTest {

    @Test
    fun `it should issue an jwk json`() {
        val issuer = mockk<JwkIssuer>()
        val signatureJwk = SignatureJwkIssuer(issuer)

        every { issuer.issue() } returns "jwk json example".right()

        val result = signatureJwk.issue()

        verify { issuer.issue() }

        assertEquals("jwk json example", result.getOrNull())
    }

    @Test
    fun `it should not issue an jwk json on error`() {
        val issuer = mockk<JwkIssuer>()
        val signatureJwk = SignatureJwkIssuer(issuer)

        every { issuer.issue() } returns JwkError().left()

        val result = signatureJwk.issue()

        verify { issuer.issue() }

        assertIs<JwkError>(result.leftOrNull())
    }

}
