package com.usadapekora.bot.infraestructure.trigger.persistence.json

import com.usadapekora.bot.domain.trigger.text.TriggerTextResponse
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseContentType
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseId
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonResourceBuiltInTriggerTextResponseRepositoryTest {

    @Test
    fun `it should return built-in trigger response text by id`() {
        val expected = TriggerTextResponse.fromPrimitives(
            id = "a1b5eb1e-fc9d-4d03-a732-291fd57599b6",
            type = TriggerTextResponseContentType.TEXT.value,
            content = "It’s a me, Pekora!"
        )

        val repository = JsonResourceBuiltInTriggerTextRepository()
        val result = repository.find(TriggerTextResponseId("a1b5eb1e-fc9d-4d03-a732-291fd57599b6"))

        assertEquals(expected, result.getOrNull(), result.leftOrNull()?.message)
    }

}
