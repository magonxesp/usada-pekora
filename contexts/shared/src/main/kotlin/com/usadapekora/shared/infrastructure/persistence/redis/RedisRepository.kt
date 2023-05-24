package com.usadapekora.shared.infrastructure.persistence.redis

import com.usadapekora.shared.redisHost
import com.usadapekora.shared.redisPort
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

abstract class RedisRepository {

    protected fun <T> redisConnection(block: (jedis: Jedis) -> T?): T? {
        val pool = JedisPool(redisHost, redisPort)
        val result = block(pool.resource)
        pool.close()
        return result
    }

}
