package com.usadapekora.context.trigger.infraestructure.persistence

import com.usadapekora.context.trigger.TriggerModuleIntegrationTest
import com.usadapekora.context.trigger.domain.TriggerAudioException
import com.usadapekora.context.trigger.infraestructure.persistence.mongodb.MongoDbTriggerAudioRepository
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class MongoDbTriggerAudioRepositoryTest : TriggerModuleIntegrationTest() {

    @Test
    fun `should find trigger audio by id`() {
        databaseTestTriggerAudio {
            val repository = MongoDbTriggerAudioRepository()
            val audio = repository.find(it.id)

            assertEquals(it, audio)
        }
    }

    @Test
    fun `should not find trigger audio by id`() {
        databaseTestTriggerAudio(save = false) {
            val repository = MongoDbTriggerAudioRepository()

            assertThrows<TriggerAudioException.NotFound> {
                repository.find(it.id)
            }
        }
    }

    @Test
    fun `should find trigger audio by trigger id`() {
        databaseTestTriggerAudio {
            val repository = MongoDbTriggerAudioRepository()
            val audio = repository.findByTrigger(it.trigger)

            assertEquals(it, audio)
        }
    }

    @Test
    fun `should not find trigger audio by trigger id`() {
        databaseTestTriggerAudio(save = false) {
            val repository = MongoDbTriggerAudioRepository()

            assertThrows<TriggerAudioException.NotFound> {
                repository.findByTrigger(it.trigger)
            }
        }
    }

    @Test
    fun `should save`() {
        databaseTestTriggerAudio(save = false) {
            val repository = MongoDbTriggerAudioRepository()
            repository.save(it)

            val audio = repository.find(it.id)
            assertEquals(it, audio)
        }
    }

    @Test
    fun `should delete`() {
        databaseTestTriggerAudio(delete = false) {
            val repository = MongoDbTriggerAudioRepository()
            repository.delete(it)

            assertThrows<TriggerAudioException.NotFound> {
                repository.find(it.id)
            }
        }
    }

}
