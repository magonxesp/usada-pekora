package com.usadapekora.bot.application.trigger

import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.application.trigger.create.text.TriggerTextResponseCreateRequest
import com.usadapekora.bot.application.trigger.create.text.TriggerTextResponseCreator
import com.usadapekora.bot.domain.trigger.response.text.TriggerTextResponseMother
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertTrue

class TriggerTextResponseCreatorTest {

    @Test
    fun `should create a text response`() {
        val textResponse = TriggerTextResponseMother.create()
        val repository = mockk<TriggerTextResponseRepository>(relaxed = true)

        every { repository.find(textResponse.id) } returns TriggerTextResponseException.NotFound().left()

        val creator = TriggerTextResponseCreator(repository)
        val result = creator.create(
            TriggerTextResponseCreateRequest(
                id = textResponse.id.value,
                content = textResponse.content.value,
                type = textResponse.type.value
            )
        )

        assertTrue(result.isRight())

        verify { repository.find(textResponse.id) }
        verify { repository.save(textResponse) }
    }

    @Test
    fun `should not create a existing text response`() {
        val textResponse = TriggerTextResponseMother.create()
        val repository = mockk<TriggerTextResponseRepository>(relaxed = true)

        every { repository.find(textResponse.id) } returns textResponse.right()

        val creator = TriggerTextResponseCreator(repository)
        val result = creator.create(
            TriggerTextResponseCreateRequest(
                id = textResponse.id.value,
                content = textResponse.content.value,
                type = textResponse.type.value
            )
        )

        assertTrue(result.leftOrNull() is TriggerTextResponseException.AlreadyExists)

        verify { repository.find(textResponse.id) }
        verify(inverse = true) { repository.save(textResponse) }
    }

}
