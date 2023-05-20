package com.usadapekora.shared.infrastructure.persistence.redis

import com.usadapekora.shared.domain.KeyValueCacheStorage
import com.usadapekora.shared.redisHost
import com.usadapekora.shared.redisPort
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.params.SetParams

class RedisKeyValueCacheStorage : KeyValueCacheStorage {

    private fun <T> redisConnection(block: (jedis: Jedis) -> T?): T? {
        val pool = JedisPool(redisHost, redisPort)
        val result = block(pool.resource)
        pool.close()
        return result
    }

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
