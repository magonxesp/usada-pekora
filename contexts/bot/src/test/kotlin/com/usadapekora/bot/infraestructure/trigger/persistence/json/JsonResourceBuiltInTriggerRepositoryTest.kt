package com.usadapekora.bot.infraestructure.trigger.persistence.json

import kotlin.test.Test
import kotlin.test.assertTrue

class JsonResourceBuiltInTriggerRepositoryTest {

    @Test
    fun `it should return built-in triggers`() {
        val repository = JsonResourceBuiltInTriggerRepository()
        val triggers = repository.findAll()

        assertTrue(triggers.isNotEmpty())
    }

}
