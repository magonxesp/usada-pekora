package com.usadapekora.auth.infrastructure.oauth.persistence.redis

import arrow.core.Either
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationGrant
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationGrantError
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationGrantRepository

class RedisOAuthAuthorizationGrantRepository : OAuthAuthorizationGrantRepository {

    override fun find(code: OAuthAuthorizationGrant.OAuthAuthorizationGrantCode): Either<OAuthAuthorizationGrantError.NotFound, OAuthAuthorizationGrant> {
        TODO("Not yet implemented")
    }

    override fun save(entity: OAuthAuthorizationGrant) {
        TODO("Not yet implemented")
    }
}
