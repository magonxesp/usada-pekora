package com.usadapekora.bot.application.trigger

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.trigger.delete.TriggerDeleter
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.TriggerMother
import com.usadapekora.bot.domain.trigger.TriggerRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertTrue

class TriggerDeleterTest {

    @Test
    fun `should delete a trigger`() {
        val trigger = TriggerMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val deleter = TriggerDeleter(repository)

        every { repository.find(trigger.id) } returns trigger.right()

        val result = deleter.delete(trigger.id.value)

        assertTrue(result.isRight())

        verify { repository.delete(trigger) }
    }

    @Test
    fun `should not delete a trigger does not exists`() {
        val trigger = TriggerMother.create()
        val repository = mockk<TriggerRepository>(relaxed = true)
        val deleter = TriggerDeleter(repository)

        every { repository.find(trigger.id) } returns TriggerException.NotFound().left()

        val result = deleter.delete(trigger.id.value)
        assertTrue(result.leftOrNull() is TriggerException.NotFound)
    }

}
