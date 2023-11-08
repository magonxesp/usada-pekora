package com.usadapekora.auth.application.jwt

import arrow.core.Either
import arrow.core.left
import com.usadapekora.auth.domain.jwt.Jwt
import com.usadapekora.auth.domain.jwt.JwtException
import com.usadapekora.auth.domain.jwt.JwtIssuer
import com.usadapekora.auth.domain.shared.AuthorizationGrant
import com.usadapekora.auth.domain.shared.AuthorizationGrantRepository

class AccessJwtIssuer(
    private val authorizationGrantRepository: AuthorizationGrantRepository,
    private val jwtIssuer: JwtIssuer,
) {
    fun issue(code: String): Either<JwtException, AccessJwtIssuerResponse> {
        val grant = authorizationGrantRepository.find(AuthorizationGrant.AuthorizationGrantCode(code))
            .onLeft { return JwtException.CodeNotFound("The authorization code is expired or invalid").left() }
            .getOrNull()!!

        return jwtIssuer.issue(grant, Jwt.JwtExpiresAt.DEFAULT_EXPIRATION_TIME)
            .map { AccessJwtIssuerResponse(it.token.value, it.expiresAt.value.toEpochMilliseconds()) }
    }
}
