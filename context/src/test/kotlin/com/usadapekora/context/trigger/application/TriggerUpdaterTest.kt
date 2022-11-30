package com.usadapekora.context.trigger.application

import com.usadapekora.context.trigger.application.update.TriggerUpdateRequest
import com.usadapekora.context.trigger.application.update.TriggerUpdater
import com.usadapekora.context.trigger.domain.Trigger
import com.usadapekora.context.trigger.domain.TriggerMother
import com.usadapekora.context.trigger.domain.TriggerRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class TriggerUpdaterTest {

    @Test
    fun `should update a trigger`() {
        val trigger = TriggerMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val updater = TriggerUpdater(repository)

        every { repository.find(trigger.id) } returns trigger

        trigger.input = Trigger.TriggerInput("New expected user input")

        updater.update(TriggerUpdateRequest(
            id = trigger.id.value,
            input = "New expected user input"
        ))

        verify { repository.save(trigger) }
    }

}
