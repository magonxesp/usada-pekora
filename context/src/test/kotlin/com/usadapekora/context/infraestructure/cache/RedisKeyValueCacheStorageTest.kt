package com.usadapekora.context.infraestructure.cache

import com.usadapekora.context.infraestructure.cache.RedisKeyValueCacheStorage
import org.junit.jupiter.api.Test
import java.util.Random
import kotlin.test.assertEquals

class RedisKeyValueCacheStorageTest {

    @Test
    fun `should save key with value`() {
        val cache = RedisKeyValueCacheStorage()
        val key = "test_${Random().nextLong()}"
        val value = Random().nextLong().toString()

        cache.set(key, value)
        val saved = cache.get(key)

        assertEquals(value, saved)
    }

    @Test
    fun `should get null on missing key`() {
        val cache = RedisKeyValueCacheStorage()
        val key = "test_${Random().nextLong()}"
        val saved = cache.get(key)

        assertEquals(null, saved)
    }

}
