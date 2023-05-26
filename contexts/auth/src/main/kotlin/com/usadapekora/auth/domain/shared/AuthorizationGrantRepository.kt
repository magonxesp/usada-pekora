package com.usadapekora.auth.domain.shared

import arrow.core.Either
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationGrantError

interface AuthorizationGrantRepository {
    fun find(code: AuthorizationGrant.AuthorizationGrantCode): Either<OAuthAuthorizationGrantError.NotFound, AuthorizationGrant>
    fun save(entity: AuthorizationGrant)
}
