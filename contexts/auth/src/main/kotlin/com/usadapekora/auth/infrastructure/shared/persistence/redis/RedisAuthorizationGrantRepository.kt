package com.usadapekora.auth.infrastructure.shared.persistence.redis

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.auth.domain.shared.AuthorizationGrant
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationGrantError
import com.usadapekora.auth.domain.shared.AuthorizationGrantRepository
import com.usadapekora.shared.infrastructure.persistence.redis.RedisRepository
import redis.clients.jedis.params.SetParams

class RedisAuthorizationGrantRepository : RedisRepository(), AuthorizationGrantRepository {

    override fun find(code: AuthorizationGrant.AuthorizationGrantCode): Either<OAuthAuthorizationGrantError.NotFound, AuthorizationGrant> {
        val authorizationCode = redisConnection {
            it.get(code.value)?.let {
                AuthorizationGrantJson.fromJsonString(it).toEntity()
            }
        }

        return authorizationCode?.right() ?: OAuthAuthorizationGrantError.NotFound("The authorization with code ${code.value} not exists").left()
    }

    override fun save(entity: AuthorizationGrant) {
        redisConnection {
            it.set(
                entity.code.value,
                AuthorizationGrantJson.fromEntity(entity).toJsonString(),
                SetParams().ex(entity.expiresAt.seconds.toLong()))
        }
    }
}
