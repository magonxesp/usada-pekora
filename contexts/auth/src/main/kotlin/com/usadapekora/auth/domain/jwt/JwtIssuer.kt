package com.usadapekora.auth.domain.jwt

import arrow.core.Either
import com.usadapekora.auth.domain.shared.AuthorizationGrant

interface JwtIssuer {
    fun issue(authorization: AuthorizationGrant, expirationTimeInSeconds: Int): Either<JwtError, Jwt>
}
