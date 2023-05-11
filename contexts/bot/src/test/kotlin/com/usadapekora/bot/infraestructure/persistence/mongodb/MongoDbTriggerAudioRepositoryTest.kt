package com.usadapekora.bot.infraestructure.persistence.mongodb

import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioDefaultMother
import com.usadapekora.bot.domain.trigger.audio.TriggerDefaultAudioResponse
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerAudioDefaultRepository
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerAudioRepository
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

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
            assertThrows<TriggerAudioResponseException.NotFound> {
                repository.find(it.id)
            }
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
            assertThrows<TriggerAudioResponseException.NotFound> {
                repository.findByTrigger(it.trigger)
            }
        }
    }

}
