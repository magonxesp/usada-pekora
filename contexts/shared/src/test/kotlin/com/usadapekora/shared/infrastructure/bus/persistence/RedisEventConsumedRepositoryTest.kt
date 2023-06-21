package com.usadapekora.shared.infrastructure.bus.persistence

import com.usadapekora.shared.domain.EventConsumedMother
import com.usadapekora.shared.infrastructure.bus.persistence.redis.RedisEventConsumedRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RedisEventConsumedRepositoryTest {

    val repository = RedisEventConsumedRepository()

    @Test
    fun `it should find a event consumed`() {
        val eventConsumed = EventConsumedMother.create()

        repository.save(eventConsumed).run {
            assertIs<Unit>(getOrNull())
        }

        repository.find(eventConsumed.id, eventConsumed.consumedBy).run {
            assertEquals(eventConsumed, getOrNull())
        }
    }

    @Test
    fun `it should save a event consumed`() {
        val eventConsumed = EventConsumedMother.create()
        val result = repository.save(eventConsumed)

        assertIs<Unit>(result.getOrNull())
    }

}
