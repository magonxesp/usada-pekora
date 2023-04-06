package com.usadapekora.bot.infraestructure.persistence.mongodb

import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioDefaultMother
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefault
import com.usadapekora.bot.domain.trigger.exception.TriggerAudioResponseException
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerAudioDefaultRepository
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class MongoDbTriggerAudioDefaultRepositoryTest : MongoDbRepositoryTest<TriggerAudioDefault, MongoDbTriggerAudioDefaultRepository>(
    repository = MongoDbTriggerAudioDefaultRepository(),
    mother = TriggerAudioDefaultMother
) {

    @Test
    fun `should find trigger audio by id`() {
        databaseTest {
            val audio = repository.find(it.id)

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
            val audio = repository.findByTrigger(it.trigger)
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

    @Test
    fun `should save`() {
        databaseTest(save = false) {
            repository.save(it)
            val audio = repository.find(it.id)
            assertEquals(it, audio)
        }
    }

    @Test
    fun `should delete`() {
        databaseTest(delete = false) {
            repository.delete(it)
            assertThrows<TriggerAudioResponseException.NotFound> {
                repository.find(it.id)
            }
        }
    }

}
