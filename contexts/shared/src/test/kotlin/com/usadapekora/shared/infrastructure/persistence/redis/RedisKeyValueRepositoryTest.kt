package com.usadapekora.shared.infrastructure.persistence.redis

import org.junit.jupiter.api.Test
import java.util.Random
import kotlin.test.assertEquals

class RedisKeyValueRepositoryTest {

    @Test
    fun `should save key with value`() {
        val cache = RedisKeyValueRepository()
        val key = "test_${Random().nextLong()}"
        val value = Random().nextLong().toString()

        cache.set(key, value)
        val saved = cache.get(key)

        assertEquals(value, saved)
    }

    @Test
    fun `should get null on missing key`() {
        val cache = RedisKeyValueRepository()
        val key = "test_${Random().nextLong()}"
        val saved = cache.get(key)

        assertEquals(null, saved)
    }

    @Test
    fun `should remove key`() {
        val cache = RedisKeyValueRepository()
        val key = "test_${Random().nextLong()}"
        val value = Random().nextLong().toString()

        cache.set(key, value)
        cache.remove(key)
        val saved = cache.get(key)

        assertEquals(null, saved)
    }

}
