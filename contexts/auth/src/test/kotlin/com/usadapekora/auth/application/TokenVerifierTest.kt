package com.usadapekora.auth.application

import com.usadapekora.auth.domain.SignatureVerifier
import io.mockk.mockk
import kotlin.test.Test

class TokenVerifierTest {

    @Test
    fun `it should verify the access token`() {
        val signatureVerifier = mockk<SignatureVerifier>()
        val verifier = TokenVerifier(signatureVerifier)


    }

}
