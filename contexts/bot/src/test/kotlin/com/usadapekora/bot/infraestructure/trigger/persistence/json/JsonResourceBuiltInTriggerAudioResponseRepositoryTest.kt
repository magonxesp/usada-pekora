package com.usadapekora.bot.infraestructure.trigger.persistence.json

import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonResourceBuiltInTriggerAudioResponseRepositoryTest {

    @Test
    fun `it should return built-in trigger response audio by id`() {
        val expected = TriggerAudioResponse.fromPrimitives(
            id = "cff9451d-6c73-4c79-8af3-52bc4102cceb",
            kind = TriggerAudioResponse.TriggerAudioResponseKind.RESOURCE.name,
            source = "/its_me_pekora.mp3"
        )

        val repository = JsonResourceBuiltInTriggerAudioRepository()
        val result = repository.find(TriggerAudioResponse.TriggerAudioResponseId("cff9451d-6c73-4c79-8af3-52bc4102cceb"))

        assertEquals(expected, result.getOrNull(), result.leftOrNull()?.message)
    }

}
