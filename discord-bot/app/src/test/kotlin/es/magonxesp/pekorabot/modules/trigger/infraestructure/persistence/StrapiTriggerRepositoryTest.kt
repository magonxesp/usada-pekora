package es.magonxesp.pekorabot.modules.trigger.infraestructure.persistence

import kotlin.test.Test
import kotlin.test.assertTrue

class StrapiTriggerRepositoryTest {

    @Test
    fun `should get all triggers`() {
        val repository = StrapiTriggerRepository()
        repository.all()
    }

    @Test
    fun `should get all triggers by discord server id`() {
        val repository = StrapiTriggerRepository()
        val triggers = repository.findByDiscordServer("754111233619132466")

        assertTrue(triggers.isNotEmpty())
    }

}
