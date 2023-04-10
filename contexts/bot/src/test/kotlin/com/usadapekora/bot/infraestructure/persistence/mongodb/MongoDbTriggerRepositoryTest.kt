package com.usadapekora.bot.infraestructure.persistence.mongodb

import com.usadapekora.bot.domain.trigger.exception.TriggerException
import com.usadapekora.bot.domain.trigger.TriggerMother
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerRepository
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MongoDbTriggerRepositoryTest : MongoDbRepositoryTest<Trigger, MongoDbTriggerRepository>(
    repository = MongoDbTriggerRepository(),
    mother = TriggerMother
) {

    @Test
    fun `should find all triggers`() {
        databaseTest {
            val triggers = repository.all()
            assertTrue(triggers.isNotEmpty())
        }
    }

    @Test
    fun `should find trigger by id`() {
        val trigger = TriggerMother.create()
        
        databaseTest(aggregate = trigger) {
            val found = repository.find(it.id)
            assertEquals(it, found)
        }
    }

    @Test
    fun `should not find trigger by id`() {
        assertThrows<TriggerException.NotFound> {
            repository.find(TriggerMother.create().id)
        }
    }

    @Test
    fun `should find trigger by discord server id`() {
        val trigger = TriggerMother.create()

        databaseTest(aggregate = trigger) {
            val triggers = repository.findByDiscordServer(it.discordGuildId)
            assertContains(triggers, it)
        }
    }

    @Test
    fun `should not find trigger by discord server id`() {
        val triggers = repository.findByDiscordServer(TriggerMother.create().discordGuildId)
        assertTrue(triggers.isEmpty())
    }

    @Test
    fun `should save`() {
        val trigger = TriggerMother.create()

        databaseTest(aggregate = trigger, save = false) {
            repository.save(it)
            val found = repository.find(it.id)
            assertEquals(it, found)
        }
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
