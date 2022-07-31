package es.magonxesp.pekorabot.modules.trigger.infraestructure.persistence

import org.junit.Test

class StrapiTriggerRepositoryTest {

    @Test
    fun testAll() {
        val repository = StrapiTriggerRepository()
        repository.all()
    }

}
