package com.usadapekora.context.shared.infraestructure.cache

import com.usadapekora.context.shared.domain.KeyValueCacheStorage
import com.usadapekora.redisHost
import com.usadapekora.redisPort
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.params.SetParams
import kotlin.concurrent.thread

class RedisKeyValueCacheStorage : KeyValueCacheStorage {

    companion object {
        private var pool: JedisPool? = null

        fun connect(): Jedis {
            if (pool == null) {
                pool = JedisPool(redisHost, redisPort)

                Runtime.getRuntime().addShutdownHook(thread(start = false) {
                    pool!!.close()
                    pool = null
                })
            }

            return pool!!.resource
        }
    }

    private fun <T> redisConnection(block: (jedis: Jedis) -> T?): T?
        = block(connect())

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
}
