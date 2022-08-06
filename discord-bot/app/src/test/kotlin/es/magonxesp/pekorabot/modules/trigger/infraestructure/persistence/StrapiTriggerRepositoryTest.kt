package es.magonxesp.pekorabot.modules.trigger.infraestructure.persistence

import es.magonxesp.pekorabot.modules.trigger.domain.TriggerMother
import org.junit.Test
import kotlin.test.assertTrue

class StrapiTriggerRepositoryTest {

    @Test
    fun testAll() {
        val repository = StrapiTriggerRepository()
        repository.all()
    }

    @Test
    fun testFindByDiscordServer() {
        val repository = StrapiTriggerRepository()
        val triggers = repository.findByDiscordServer("754111233619132466")

        assertTrue(triggers.isNotEmpty())
    }

}
