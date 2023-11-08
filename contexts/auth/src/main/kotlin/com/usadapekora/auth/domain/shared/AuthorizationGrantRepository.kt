package com.usadapekora.auth.domain.shared

import arrow.core.Either

interface AuthorizationGrantRepository {
    fun find(code: AuthorizationGrant.AuthorizationGrantCode): Either<AuthorizationGrantException.NotFound, AuthorizationGrant>
    fun save(entity: AuthorizationGrant)
}
