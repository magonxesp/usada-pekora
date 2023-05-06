package com.usadapekora.bot.infraestructure.persistence.mongodb

import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.TriggerMother
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.MongoDbTriggerRepository
import com.usadapekora.bot.infraestructure.persistence.mongodb.trigger.TriggerDocument
import org.junit.jupiter.api.assertThrows
import org.litote.kmongo.eq
import org.litote.kmongo.getCollectionOfName
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
    fun `should update`() {
        val connection = MongoDbRepository.connect()
        val trigger = TriggerMother.create()

        databaseTest(aggregate = trigger) {
            it.input = Trigger.TriggerInput("another input")
            repository.save(it)


            val found = connection.getCollectionOfName<TriggerDocument>("triggers")
                .find(TriggerDocument::id eq it.id.value).toList()

            assertTrue(found.size == 1)
            assertEquals(it, found.first().toEntity())
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
