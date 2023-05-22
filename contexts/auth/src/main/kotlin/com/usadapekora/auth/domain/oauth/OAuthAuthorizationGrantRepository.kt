package com.usadapekora.auth.domain.oauth

import arrow.core.Either

interface OAuthAuthorizationGrantRepository {
    fun find(code: OAuthAuthorizationGrant.OAuthAuthorizationGrantCode): Either<OAuthAuthorizationGrantError.NotFound, OAuthAuthorizationGrant>
    fun save(entity: OAuthAuthorizationGrant)
}
