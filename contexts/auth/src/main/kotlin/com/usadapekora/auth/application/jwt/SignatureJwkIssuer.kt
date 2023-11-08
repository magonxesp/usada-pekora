package com.usadapekora.auth.application.jwt

import arrow.core.Either
import com.usadapekora.auth.domain.jwt.JwkException
import com.usadapekora.auth.domain.jwt.JwkIssuer

class SignatureJwkIssuer(private val jwkIssuer: JwkIssuer) {

    fun issue(): Either<JwkException, String>
        = jwkIssuer.issue()

}
