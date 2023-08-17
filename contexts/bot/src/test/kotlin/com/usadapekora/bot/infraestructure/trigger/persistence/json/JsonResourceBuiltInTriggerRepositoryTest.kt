package com.usadapekora.bot.infraestructure.trigger.persistence.json

import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerKind
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JsonResourceBuiltInTriggerRepositoryTest {

    @Test
    fun `it should return built-in triggers`() {
        val repository = JsonResourceBuiltInTriggerRepository()
        val triggers = repository.findAll()

        assertTrue(triggers.isNotEmpty())
    }

    @Test
    fun `it should return built-in trigger by id`() {
        val expected = Trigger.fromPrimitives(
            id = "84c6a7f1-1b1d-4f59-a3d3-13d0cb0db65d",
            title = "It’s a me, Pekora!",
            input = "peko",
            compare = "in",
            responseTextId = "a1b5eb1e-fc9d-4d03-a732-291fd57599b6",
            responseAudioId = "cff9451d-6c73-4c79-8af3-52bc4102cceb",
            kind = TriggerKind.BUILT_IN.value,
            guildId = null
        )

        val repository = JsonResourceBuiltInTriggerRepository()
        val result = repository.find(Trigger.TriggerId("84c6a7f1-1b1d-4f59-a3d3-13d0cb0db65d"))

        assertEquals(expected, result.getOrNull(), result.leftOrNull()?.message)
    }

}
