package com.usadapekora.bot.application.trigger

import com.usadapekora.bot.application.trigger.create.text.TriggerTextResponseCreateRequest
import com.usadapekora.bot.application.trigger.create.text.TriggerTextResponseCreator
import com.usadapekora.bot.domain.trigger.response.text.TriggerTextResponseMother
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class TriggerTextResponseCreatorTest {

    @Test
    fun `should create a text response`() {
        val textResponse = TriggerTextResponseMother.create()
        val repository = mockk<TriggerTextResponseRepository>(relaxed = true)

        every { repository.find(textResponse.id) } throws TriggerTextResponseException.NotFound()

        val creator = TriggerTextResponseCreator(repository)

        creator.create(
            TriggerTextResponseCreateRequest(
                id = textResponse.id.value,
                content = textResponse.content.value,
                type = textResponse.type.value
            )
        )

        verify { repository.find(textResponse.id) }
        verify { repository.save(textResponse) }
    }

    @Test
    fun `should not create a existing text response`() {
        val textResponse = TriggerTextResponseMother.create()
        val repository = mockk<TriggerTextResponseRepository>(relaxed = true)

        every { repository.find(textResponse.id) } returns textResponse

        val creator = TriggerTextResponseCreator(repository)

        assertThrows<TriggerTextResponseException.AlreadyExists> {
            creator.create(
                TriggerTextResponseCreateRequest(
                    id = textResponse.id.value,
                    content = textResponse.content.value,
                    type = textResponse.type.value
                )
            )
        }

        verify { repository.find(textResponse.id) }
        verify(inverse = true) { repository.save(textResponse) }
    }

}
