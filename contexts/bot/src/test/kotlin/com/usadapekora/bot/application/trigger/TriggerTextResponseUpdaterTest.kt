package com.usadapekora.bot.application.trigger

import arrow.core.right
import com.usadapekora.bot.application.trigger.update.text.TriggerTextResponseUpdateRequest
import com.usadapekora.bot.application.trigger.update.text.TriggerTextResponseUpdater
import com.usadapekora.bot.domain.Random
import com.usadapekora.bot.domain.trigger.TriggerTextResponseMother
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponse
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class TriggerTextResponseUpdaterTest {

    @Test
    fun `should update a existing trigger text response`() {
        val textResponse = TriggerTextResponseMother.create()
        val repository = mockk<TriggerTextResponseRepository>(relaxed = true)
        val updater = TriggerTextResponseUpdater(repository)

        every { repository.find(textResponse.id) } returns textResponse.right()

        val request = TriggerTextResponseUpdateRequest(
            id = textResponse.id.value,
            values = TriggerTextResponseUpdateRequest.NewValues(
                content = Random.instance().lorem.words(),
            )
        )

        textResponse.content = TriggerTextResponse.TriggerTextResponseContent(request.values.content!!)

        updater.update(request)

        verify { repository.find(textResponse.id) }
        verify { repository.save(textResponse) }
    }

    @Test
    fun `should not update a not existing trigger text response`() {
        val textResponse = TriggerTextResponseMother.create()
        val repository = mockk<TriggerTextResponseRepository>(relaxed = true)
        val updater = TriggerTextResponseUpdater(repository)

        every { repository.find(textResponse.id) } throws TriggerTextResponseException.NotFound()

        val request = TriggerTextResponseUpdateRequest(
            id = textResponse.id.value,
            values = TriggerTextResponseUpdateRequest.NewValues(
                content = Random.instance().lorem.words(),
            )
        )

        assertThrows<TriggerTextResponseException.NotFound> {
            updater.update(request)
        }

        verify { repository.find(textResponse.id) }
        verify(inverse = true) { repository.save(textResponse) }
    }

}
