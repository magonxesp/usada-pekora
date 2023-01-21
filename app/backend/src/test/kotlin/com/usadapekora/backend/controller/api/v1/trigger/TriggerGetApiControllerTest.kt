package com.usadapekora.backend.controller.api.v1.trigger

import com.usadapekora.backend.SpringBootHttpTestCase
import com.usadapekora.backend.uglifyJson
import com.usadapekora.context.application.trigger.create.TriggerAudioCreateRequest
import com.usadapekora.context.application.trigger.create.TriggerAudioCreator
import com.usadapekora.context.application.trigger.create.TriggerCreateRequest
import com.usadapekora.context.application.trigger.create.TriggerCreator
import com.usadapekora.context.domain.trigger.Trigger
import com.usadapekora.context.domain.trigger.TriggerAudio
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.UUID
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals


class TriggerGetApiControllerTest : SpringBootHttpTestCase() {

    private fun request(url: String)
        = mockMvc.perform(
            MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

    @Test
    fun `should find a trigger by id`() {
        val creator: TriggerCreator by inject(TriggerCreator::class.java)
        val id = UUID.randomUUID().toString()
        creator.create(
            TriggerCreateRequest(
                id = id,
                input = "peko",
                compare = "in",
                outputText = "It's a me pekora",
                discordGuildId = "94101459"
            )
        )

        val expectedBody = """
            {
                "id": "$id",
                "input": "peko",
                "compare": "in",
                "outputText": "It's a me pekora",
                "discordGuildId": "94101459"
            }
        """.uglifyJson()

        request("/api/v1/trigger/${id}").andExpect {
            assertEquals(200, it.response.status)
            assertEquals(expectedBody, it.response.contentAsString)
        }
    }

    @Test
    fun `should not find a trigger by id`() {
        request("/api/v1/trigger/e322b3ac-2d30-4eff-afdc-3504f66ac4ba").andExpect {
            assertEquals(404, it.response.status)
        }
    }

    @Test
    fun `should find a triggers by discord guild id`() {
        val creator: TriggerCreator by inject(TriggerCreator::class.java)
        val id = UUID.randomUUID().toString()
        val guildId = Random.nextLong(100000000, 999999999).toString()

        creator.create(
            TriggerCreateRequest(
                id = id,
                input = "peko",
                compare = "in",
                outputText = "It's a me pekora",
                discordGuildId = guildId
            )
        )

        val expectedBody = """
            {
                "triggers": [
                    {
                        "id": "$id",
                        "input": "peko",
                        "compare": "in",
                        "outputText": "It's a me pekora",
                        "discordGuildId": "$guildId"
                    }
                ]
            }
        """.uglifyJson()

        request("/api/v1/trigger/guild/$guildId").andExpect {
            assertEquals(200, it.response.status)
            assertEquals(expectedBody, it.response.contentAsString)
        }
    }

    @Test
    fun `should not find a triggers by discord guild id`() {
        val guildId = Random.nextLong(100000000, 999999999).toString()
        val expectedBody = """
            {
                "triggers": []
            }
        """.uglifyJson()

        request("/api/v1/trigger/guild/$guildId").andExpect {
            assertEquals(200, it.response.status)
            assertEquals(expectedBody, it.response.contentAsString)
        }
    }

}
