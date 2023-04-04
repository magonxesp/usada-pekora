package com.usadapekora.bot.infraestructure.persistence.mongodb

import com.usadapekora.bot.domain.TriggerAudioMother
import com.usadapekora.bot.domain.trigger.TriggerAudio
import com.usadapekora.bot.domain.trigger.TriggerAudioException
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerAudioRepository
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class MongoDbTriggerAudioRepositoryTest : MongoDbRepositoryTest<TriggerAudio, MongoDbTriggerAudioRepository>(
    repository = MongoDbTriggerAudioRepository(),
    mother = TriggerAudioMother
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
            assertThrows<TriggerAudioException.NotFound> {
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
            assertThrows<TriggerAudioException.NotFound> {
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
            assertThrows<TriggerAudioException.NotFound> {
                repository.find(it.id)
            }
        }
    }

}
