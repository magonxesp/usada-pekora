package com.usadapekora.bot.application.trigger

import com.usadapekora.bot.application.trigger.update.TriggerUpdateRequest
import com.usadapekora.bot.application.trigger.update.TriggerUpdater
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.TriggerMother
import com.usadapekora.bot.domain.trigger.TriggerRepository
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
            values = TriggerUpdateRequest.NewValues(
                input = "New expected user input"
            )
        ))

        verify { repository.save(trigger) }
    }

}
