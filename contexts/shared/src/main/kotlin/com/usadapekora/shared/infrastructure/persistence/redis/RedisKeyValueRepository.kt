package com.usadapekora.shared.infrastructure.persistence.redis

import com.usadapekora.shared.domain.KeyValueRepository
import redis.clients.jedis.params.SetParams

class RedisKeyValueRepository : RedisRepository(), KeyValueRepository {

    override fun set(key: String, value: String) {
        redisConnection {
            it.set(key, value, SetParams().also { setParams ->
                setParams.px(86400000)
            })
        }
    }

    override fun get(key: String): String? {
        return redisConnection {
            it.get(key)
        }
    }

    override fun remove(key: String) {
        redisConnection {
            it.del(key)
        }
    }
}
