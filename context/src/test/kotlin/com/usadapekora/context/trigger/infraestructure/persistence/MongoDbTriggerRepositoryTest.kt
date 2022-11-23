package com.usadapekora.context.trigger.infraestructure.persistence

import com.usadapekora.context.trigger.TriggerModuleIntegrationTest
import com.usadapekora.context.trigger.domain.TriggerException
import com.usadapekora.context.trigger.domain.TriggerMother
import com.usadapekora.context.trigger.infraestructure.persistence.mongodb.MongoDbTriggerRepository
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MongoDbTriggerRepositoryTest : TriggerModuleIntegrationTest() {

    @Test
    fun `should find all triggers`() {
        databaseTestTrigger {
            val repository = MongoDbTriggerRepository()
            val triggers = repository.all()

            assertTrue(triggers.isNotEmpty())
        }
    }

    @Test
    fun `should find trigger by id`() {
        databaseTestTrigger {
            val repository = MongoDbTriggerRepository()
            val trigger = repository.find(it.id)

            assertEquals(it, trigger)
        }
    }

    @Test
    fun `should not find trigger by id`() {
        assertThrows<TriggerException.NotFound> {
            val repository = MongoDbTriggerRepository()
            repository.find(TriggerMother.create().id)
        }
    }

    @Test
    fun `should find trigger by discord server id`() {
        databaseTestTrigger {
            val repository = MongoDbTriggerRepository()
            val triggers = repository.findByDiscordServer(it.discordGuildId)

            assertContains(triggers, it)
        }
    }

    @Test
    fun `should not find trigger by discord server id`() {
        val repository = MongoDbTriggerRepository()
        val triggers = repository.findByDiscordServer(TriggerMother.create().discordGuildId)
        assertTrue(triggers.isEmpty())
    }

    @Test
    fun `should save`() {
        databaseTestTrigger(save = false) {
            val repository = MongoDbTriggerRepository()
            repository.save(it)
            val trigger = repository.find(it.id)

            assertEquals(it, trigger)
        }
    }

    @Test
    fun `should delete`() {
        databaseTestTrigger(delete = false) {
            val repository = MongoDbTriggerRepository()
            repository.delete(it)

            assertThrows<TriggerException.NotFound> {
                repository.find(it.id)
            }
        }
    }
}
