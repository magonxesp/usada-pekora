package com.usadapekora.context.trigger.infraestructure.persistence

import com.usadapekora.context.trigger.infraestructure.persistence.StrapiTriggerRepository
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class StrapiTriggerRepositoryTest {

    @Test
    fun `should get all triggers`() {
        val repository = StrapiTriggerRepository()

        runBlocking {
            repository.all()
        }
    }

    @Test
    fun `should get all triggers by discord server id`() {
        val repository = StrapiTriggerRepository()

        runBlocking {
            repository.findByDiscordServer("754111233619132466")
        }
    }

}
