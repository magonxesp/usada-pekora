package com.usadapekora.bot.infraestructure.persistence.mongodb

import com.usadapekora.bot.domain.trigger.TriggerTextResponse
import com.usadapekora.bot.domain.trigger.response.text.TriggerTextMother
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerTextRepository
import kotlin.test.Test
import kotlin.test.assertEquals

class MongoDbTriggerTextRepositoryTest : MongoDbRepositoryTest<TriggerTextResponse, MongoDbTriggerTextRepository>(
    repository = MongoDbTriggerTextRepository(),
    mother = TriggerTextMother
) {

    @Test
    fun `should find by id`() {
        databaseTest {
            val triggerText = repository.find(it.id)
            assertEquals(it, triggerText)
        }
    }

}
