package com.usadapekora.context.trigger.application

import com.usadapekora.context.trigger.application.create.TriggerCreateRequest
import com.usadapekora.context.trigger.application.create.TriggerCreator
import com.usadapekora.context.trigger.domain.TriggerMother
import com.usadapekora.context.trigger.domain.TriggerRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class TriggerCreatorTest {

    @Test
    fun `should create and save a trigger`() {
        val trigger = TriggerMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val creator = TriggerCreator(repository)

        creator.create(TriggerCreateRequest(
            id = trigger.id.value,
            input = trigger.input.value,
            compare = trigger.compare.value,
            outputText = trigger.outputText.value,
            discordGuildId = trigger.discordGuildId.value
        ))

        verify { repository.save(trigger) }
    }

    @Test
    fun `should not create and save a trigger`() {
        val trigger = TriggerMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val creator = TriggerCreator(repository)

        every { repository.save(trigger) } throws Exception()

        assertThrows<Exception> {
            creator.create(TriggerCreateRequest(
                id = trigger.id.value,
                input = trigger.input.value,
                compare = trigger.compare.value,
                outputText = trigger.outputText.value,
                discordGuildId = trigger.discordGuildId.value
            ))
        }
    }

}
