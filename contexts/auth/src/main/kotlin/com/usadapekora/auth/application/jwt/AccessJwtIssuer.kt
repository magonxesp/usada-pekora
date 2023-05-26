package com.usadapekora.auth.application.jwt

import arrow.core.Either
import com.usadapekora.auth.domain.jwt.JwtIssuer
import com.usadapekora.auth.domain.jwt.Jwt
import com.usadapekora.auth.domain.jwt.JwtError
import com.usadapekora.auth.domain.shared.AuthorizationGrantRepository
import kotlinx.datetime.Clock

class AccessJwtIssuer(
    private val authorizationGrantRepository: AuthorizationGrantRepository,
    private val jwtIssuer: JwtIssuer,
    private val clock: Clock
) {
    fun issue(code: String): Either<JwtError, Jwt> {
        TODO("Not yet implemented")
    }
}
