package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioResponseMother
import com.usadapekora.shared.MongoDbRepositoryTestCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MongoDbTriggerAudioResponseRepositoryTest : MongoDbRepositoryTestCase<TriggerAudioResponse, MongoDbTriggerAudioResponseRepository>(
    repository = MongoDbTriggerAudioResponseRepository(),
    mother = TriggerAudioResponseMother
) {

    @Test
    fun `it should find trigger audio by id`() {
        runMongoDbRepositoryTest<TriggerAudioResponseDocument>(TriggerAudioResponseDocument.Companion) {
            val audio = repository.find(it.id).getOrNull()

            assertEquals(it, audio)
        }
    }

    @Test
    fun `it should not find trigger audio by id`() {
        runMongoDbRepositoryTest<TriggerAudioResponseDocument>(TriggerAudioResponseDocument.Companion, save = false) {
            val result = repository.find(it.id)
            assertTrue(result.leftOrNull() is TriggerAudioResponseException.NotFound)
        }
    }

    @Test
    fun `it should find trigger audio by trigger id`() {
//        runMongoDbRepositoryTest<TriggerAudioResponseDocument>(TriggerAudioResponseDocument.Companion) {
//            val audio = repository.findByTrigger(it.trigger).getOrNull()
//            assertEquals(it, audio)
//        }
    }

    @Test
    fun `it should not find trigger audio by trigger id`() {
//        runMongoDbRepositoryTest<TriggerAudioResponseDocument>(TriggerAudioResponseDocument.Companion, save = false) {
//            val result = repository.findByTrigger(it.trigger)
//            assertTrue(result.leftOrNull() is TriggerAudioResponseException.NotFound)
//        }
    }

    @Test
    fun `it should save`() {
        runMongoDbRepositoryTest<TriggerAudioResponseDocument>(TriggerAudioResponseDocument.Companion, save = false) {
            repository.save(it)
            val audio = repository.find(it.id).getOrNull()
            assertEquals(it, audio)
        }
    }

    @Test
    fun `it should delete`() {
        runMongoDbRepositoryTest<TriggerAudioResponseDocument>(TriggerAudioResponseDocument.Companion, delete = false) {
            repository.delete(it)

            val result = repository.find(it.id)
            assertTrue(result.leftOrNull() is TriggerAudioResponseException.NotFound)
        }
    }

}
