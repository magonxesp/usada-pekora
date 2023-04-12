package com.usadapekora.bot.infraestructure.persistence.mongodb

import com.usadapekora.bot.domain.trigger.text.TriggerTextResponse
import com.usadapekora.bot.domain.trigger.response.text.TriggerTextResponseMother
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerTextRepository
import kotlin.test.Test
import kotlin.test.assertEquals

class MongoDbTriggerTextRepositoryTest : MongoDbRepositoryTest<TriggerTextResponse, MongoDbTriggerTextRepository>(
    repository = MongoDbTriggerTextRepository(),
    mother = TriggerTextResponseMother
) {

    @Test
    fun `should find by id`() {
        databaseTest {
            val triggerText = repository.find(it.id)
            assertEquals(it, triggerText)
        }
    }

    @Test
    fun `should save`() {
        databaseTest(save = false) {
            repository.save(it)
            val saved = repository.find(it.id)
            assertEquals(it, saved)
        }
    }

}
