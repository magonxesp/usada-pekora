package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import com.usadapekora.bot.domain.trigger.response.text.TriggerTextResponseMother
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponse
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.bot.infraestructure.MongoDbRepositoryTestCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MongoDbTriggerTextRepositoryTest : MongoDbRepositoryTestCase<TriggerTextResponse, MongoDbTriggerTextRepository>(
    repository = MongoDbTriggerTextRepository(),
    mother = TriggerTextResponseMother
) {

    @Test
    fun `should find by id`() {
        databaseTest {
            val triggerText = repository.find(it.id).getOrNull()
            assertEquals(it, triggerText)
        }
    }

    @Test
    fun `should save`() {
        databaseTest(save = false) {
            repository.save(it)
            val saved = repository.find(it.id).getOrNull()
            assertEquals(it, saved)
        }
    }

    @Test
    fun `should delete`() {
        databaseTest(delete = false) {
            repository.delete(it)

            val result = repository.find(it.id)
            assertTrue(result.leftOrNull() is TriggerTextResponseException.NotFound)
        }
    }

}
