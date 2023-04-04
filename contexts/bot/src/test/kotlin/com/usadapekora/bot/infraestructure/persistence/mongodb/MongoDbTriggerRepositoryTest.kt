package com.usadapekora.bot.infraestructure.persistence.mongodb

import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.TriggerMother
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
        databaseTest {
            val trigger = repository.find(it.id)
            assertEquals(it, trigger)
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
        databaseTest {
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
        databaseTest(save = false) {
            repository.save(it)
            val trigger = repository.find(it.id)
            assertEquals(it, trigger)
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
