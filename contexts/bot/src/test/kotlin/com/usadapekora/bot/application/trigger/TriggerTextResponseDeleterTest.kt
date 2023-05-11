package com.usadapekora.bot.application.trigger

import arrow.core.right
import com.usadapekora.bot.application.trigger.delete.text.TriggerTextResponseDeleter
import com.usadapekora.bot.domain.trigger.response.text.TriggerTextResponseMother
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class TriggerTextResponseDeleterTest {

    @Test
    fun `should delete a text response`() {
        val textResponse = TriggerTextResponseMother.create()
        val repository = mockk<TriggerTextResponseRepository>(relaxed = true)
        val deleter = TriggerTextResponseDeleter(repository)

        every { repository.find(textResponse.id) } returns textResponse.right()

        deleter.delete(textResponse.id.value)

        verify { repository.find(textResponse.id) }
        verify { repository.delete(textResponse) }
    }

    @Test
    fun `should not delete a not existing text response`() {
        val textResponse = TriggerTextResponseMother.create()
        val repository = mockk<TriggerTextResponseRepository>(relaxed = true)
        val deleter = TriggerTextResponseDeleter(repository)

        every { repository.find(textResponse.id) } throws TriggerTextResponseException.NotFound()

        assertThrows<TriggerTextResponseException.NotFound> {
            deleter.delete(textResponse.id.value)
        }

        verify { repository.find(textResponse.id) }
        verify(inverse = true) { repository.delete(textResponse) }
    }

}
