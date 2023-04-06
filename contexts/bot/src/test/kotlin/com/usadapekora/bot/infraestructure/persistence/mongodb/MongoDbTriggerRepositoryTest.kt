package com.usadapekora.bot.infraestructure.persistence.mongodb

import com.usadapekora.bot.domain.trigger.exception.TriggerException
import com.usadapekora.bot.domain.trigger.TriggerMother
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerTextResponse
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefault
import com.usadapekora.bot.domain.trigger.response.audio.TriggerAudioDefaultMother
import com.usadapekora.bot.domain.trigger.response.text.TriggerTextMother
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerAudioDefaultRepository
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerAudioRepository
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerRepository
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerTextRepository
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MongoDbTriggerRepositoryTest : MongoDbRepositoryTest<Trigger, MongoDbTriggerRepository>(
    repository = MongoDbTriggerRepository(MongoDbTriggerAudioRepository(), MongoDbTriggerTextRepository()),
    mother = TriggerMother
) {
    private val textAudioRepository = MongoDbTriggerTextRepository()
    private val defaultAudioRepository = MongoDbTriggerAudioDefaultRepository()

    private fun saveRelations(responseText: TriggerTextResponse, responseAudio: TriggerAudioDefault) {
        textAudioRepository.save(responseText)
        defaultAudioRepository.save(responseAudio)
    }

    private fun deleteRelations(responseText: TriggerTextResponse, responseAudio: TriggerAudioDefault) {
        textAudioRepository.delete(responseText)
        defaultAudioRepository.delete(responseAudio)
    }

    @Test
    fun `should find all triggers`() {
        databaseTest {
            val triggers = repository.all()
            assertTrue(triggers.isNotEmpty())
        }
    }

    @Test
    fun `should find trigger by id`() {
        val audioResponse = TriggerAudioDefaultMother.create()
        val textResponse = TriggerTextMother.create()
        val trigger = TriggerMother.create(responseText = textResponse, responseAudio = audioResponse)

        saveRelations(textResponse, audioResponse)

        databaseTest(aggregate = trigger) {
            val found = repository.find(it.id)
            assertEquals(it, found)
        }

        deleteRelations(textResponse, audioResponse)
    }

    @Test
    fun `should not find trigger by id`() {
        assertThrows<TriggerException.NotFound> {
            repository.find(TriggerMother.create().id)
        }
    }

    @Test
    fun `should find trigger by discord server id`() {
        val audioResponse = TriggerAudioDefaultMother.create()
        val textResponse = TriggerTextMother.create()
        val trigger = TriggerMother.create(responseText = textResponse, responseAudio = audioResponse)

        saveRelations(textResponse, audioResponse)

        databaseTest(aggregate = trigger) {
            val triggers = repository.findByDiscordServer(it.discordGuildId)
            assertContains(triggers, it)
        }

        deleteRelations(textResponse, audioResponse)
    }

    @Test
    fun `should not find trigger by discord server id`() {
        val triggers = repository.findByDiscordServer(TriggerMother.create().discordGuildId)
        assertTrue(triggers.isEmpty())
    }

    @Test
    fun `should save`() {
        val audioResponse = TriggerAudioDefaultMother.create()
        val textResponse = TriggerTextMother.create()
        val trigger = TriggerMother.create(responseText = textResponse, responseAudio = audioResponse)

        saveRelations(textResponse, audioResponse)

        databaseTest(aggregate = trigger, save = false) {
            repository.save(it)
            val found = repository.find(it.id)
            assertEquals(it, found)
        }

        deleteRelations(textResponse, audioResponse)
    }

    @Test
    fun `should delete`() {
        databaseTest(delete = false) {
            repository.delete(it)
            assertThrows<TriggerException.NotFound> {
                repository.find(it.id)
            }
        }
    }
}
