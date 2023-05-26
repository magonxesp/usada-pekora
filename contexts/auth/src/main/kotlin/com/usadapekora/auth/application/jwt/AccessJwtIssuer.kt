package com.usadapekora.auth.application.jwt

import arrow.core.Either
import arrow.core.left
import com.usadapekora.auth.domain.jwt.JwtIssuer
import com.usadapekora.auth.domain.jwt.Jwt
import com.usadapekora.auth.domain.jwt.JwtError
import com.usadapekora.auth.domain.shared.AuthorizationGrant
import com.usadapekora.auth.domain.shared.AuthorizationGrantRepository
import kotlinx.datetime.Clock

class AccessJwtIssuer(
    private val authorizationGrantRepository: AuthorizationGrantRepository,
    private val jwtIssuer: JwtIssuer,
    private val clock: Clock
) {
    fun issue(code: String): Either<JwtError, Jwt> {
        val grant = authorizationGrantRepository.find(AuthorizationGrant.AuthorizationGrantCode(code))
            .onLeft { return JwtError.CodeNotFound("The authorization code is expired or invalid").left() }
            .getOrNull()!!

        return jwtIssuer.issue(grant, Jwt.JwtExpiresAt.DEFAULT_EXPIRATION_TIME)
    }
}
