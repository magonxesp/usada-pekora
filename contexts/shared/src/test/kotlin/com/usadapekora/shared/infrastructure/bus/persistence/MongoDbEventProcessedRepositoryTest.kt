package com.usadapekora.shared.infrastructure.bus.persistence

import com.usadapekora.shared.MongoDbRepositoryTestCase
import com.usadapekora.shared.domain.EventProcessedMother
import com.usadapekora.shared.domain.bus.event.EventProcessed
import com.usadapekora.shared.infrastructure.bus.event.persistence.mongodb.EventProcessedDocument
import com.usadapekora.shared.infrastructure.bus.persistence.mongodb.MongoDbEventProcessedRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class MongoDbEventProcessedRepositoryTest : MongoDbRepositoryTestCase<EventProcessed, MongoDbEventProcessedRepository>(
    repository = MongoDbEventProcessedRepository(),
    mother = EventProcessedMother
) {

    @Test
    fun `it should find a processed event`() {
        runMongoDbRepositoryTest<EventProcessedDocument>(EventProcessedDocument.Companion) {
            val result = repository.find(it.id, it.consumedBy)
            assertEquals(it, result.getOrNull())
        }
    }

    @Test
    fun `it should save a processed event`() {
        runMongoDbRepositoryTest<EventProcessedDocument>(EventProcessedDocument.Companion, save = false) {
            repository.save(it).run {
                assertIs<Unit>(getOrNull())
            }

            val result = repository.find(it.id, it.consumedBy)
            assertEquals(it, result.getOrNull())
        }
    }

}
