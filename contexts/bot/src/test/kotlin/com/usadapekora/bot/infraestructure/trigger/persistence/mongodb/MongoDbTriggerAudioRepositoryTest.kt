package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerDefaultAudioResponse
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioDefaultMother
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MongoDbTriggerAudioRepositoryTest {

    private val providerRepository = MongoDbTriggerAudioDefaultRepository()
    private val repository = MongoDbTriggerAudioRepository()

    /**
     * Creates a test trigger and delete from the database after test
     */
    private fun databaseTest(
        aggregate: TriggerDefaultAudioResponse = TriggerAudioDefaultMother.random(),
        save: Boolean = true,
        delete: Boolean = true,
        test: (aggregate: TriggerDefaultAudioResponse) -> Unit
    ) {
        if (save) {
            providerRepository.save(aggregate)
        }

        test(aggregate)

        if (delete) {
            providerRepository.delete(aggregate)
        }
    }

    @Test
    fun `should find trigger audio by id`() {
        databaseTest {
            val audio = repository.find(it.id).getOrNull()

            assertEquals(it, audio)
        }
    }

    @Test
    fun `should not find trigger audio by id`() {
        databaseTest(save = false) {
            val result = repository.find(it.id)
            assertTrue(result.leftOrNull() is TriggerAudioResponseException.NotFound)
        }
    }

    @Test
    fun `should find trigger audio by trigger id`() {
        databaseTest {
            val audio = repository.findByTrigger(it.trigger).getOrNull()
            assertEquals(it, audio)
        }
    }

    @Test
    fun `should not find trigger audio by trigger id`() {
        databaseTest(save = false) {
            val result = repository.findByTrigger(it.trigger)
            assertTrue(result.leftOrNull() is TriggerAudioResponseException.NotFound)
        }
    }

}
