package com.usadapekora.bot.application.trigger

import arrow.core.right
import com.usadapekora.bot.application.trigger.find.text.TriggerTextResponseFindResponse
import com.usadapekora.bot.application.trigger.find.text.TriggerTextResponseFinder
import com.usadapekora.bot.domain.trigger.TriggerTextResponseMother
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerTextResponseFinderTest {

    @Test
    fun `should find trigger text response by id`() {
        val textResponse = TriggerTextResponseMother.create()
        val repository = mockk<TriggerTextResponseRepository>()
        val finder = TriggerTextResponseFinder(repository)

        every { repository.find(textResponse.id) } returns textResponse.right()

        val response = TriggerTextResponseFindResponse(
            id = textResponse.id.value,
            content = textResponse.content.value,
            type = textResponse.type.value
        )

        val actual = finder.find(textResponse.id.value).getOrNull()

        assertEquals(response, actual)

        verify { repository.find(textResponse.id) }
    }

}
