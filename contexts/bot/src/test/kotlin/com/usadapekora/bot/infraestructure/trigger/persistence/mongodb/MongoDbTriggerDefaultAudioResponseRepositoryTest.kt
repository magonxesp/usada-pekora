package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioDefaultMother
import com.usadapekora.bot.domain.trigger.audio.TriggerDefaultAudioResponse
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.infraestructure.MongoDbRepositoryTestCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MongoDbTriggerDefaultAudioResponseRepositoryTest : MongoDbRepositoryTestCase<TriggerDefaultAudioResponse, MongoDbTriggerAudioDefaultRepository>(
    repository = MongoDbTriggerAudioDefaultRepository(),
    mother = TriggerAudioDefaultMother
) {

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

    @Test
    fun `should save`() {
        databaseTest(save = false) {
            repository.save(it)
            val audio = repository.find(it.id).getOrNull()
            assertEquals(it, audio)
        }
    }

    @Test
    fun `should delete`() {
        databaseTest(delete = false) {
            repository.delete(it)

            val result = repository.find(it.id)
            assertTrue(result.leftOrNull() is TriggerAudioResponseException.NotFound)
        }
    }

}
