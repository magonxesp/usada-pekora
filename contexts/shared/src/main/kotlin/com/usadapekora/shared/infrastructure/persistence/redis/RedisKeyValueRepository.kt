package com.usadapekora.shared.infrastructure.persistence.redis

import com.usadapekora.shared.domain.common.KeyValueRepository
import com.usadapekora.shared.redisHost
import com.usadapekora.shared.redisPort
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
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
