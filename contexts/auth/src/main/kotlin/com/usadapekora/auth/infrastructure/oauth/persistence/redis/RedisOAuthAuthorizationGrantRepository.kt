package com.usadapekora.auth.infrastructure.oauth.persistence.redis

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationGrant
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationGrantError
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationGrantRepository
import com.usadapekora.shared.infrastructure.persistence.redis.RedisRepository
import redis.clients.jedis.params.SetParams

class RedisOAuthAuthorizationGrantRepository : RedisRepository(), OAuthAuthorizationGrantRepository {

    override fun find(code: OAuthAuthorizationGrant.OAuthAuthorizationGrantCode): Either<OAuthAuthorizationGrantError.NotFound, OAuthAuthorizationGrant> {
        val authorizationCode = redisConnection {
            it.get(code.value)?.let {
                OAuthAuthorizationGrantJson.fromJsonString(it).toEntity()
            }
        }

        return authorizationCode?.right() ?: OAuthAuthorizationGrantError.NotFound("The authorization with code ${code.value} not exists").left()
    }

    override fun save(entity: OAuthAuthorizationGrant) {
        redisConnection {
            it.set(
                entity.code.value,
                OAuthAuthorizationGrantJson.fromEntity(entity).toJsonString(),
                SetParams().ex(entity.expiresAt.seconds.toLong()))
        }
    }
}
