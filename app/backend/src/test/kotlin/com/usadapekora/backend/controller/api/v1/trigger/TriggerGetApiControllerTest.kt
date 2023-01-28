package com.usadapekora.backend.controller.api.v1.trigger

import com.usadapekora.backend.uglifyJson
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.UUID
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals


class TriggerGetApiControllerTest : TriggerControllerTest() {

    private fun request(url: String)
        = mockMvc.perform(
            MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

    @Test
    fun `should find a trigger by id`() {
        val id = UUID.randomUUID().toString()
        createDummy(id = id)

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
        val id = UUID.randomUUID().toString()
        val guildId = Random.nextLong(100000000, 999999999).toString()
        createDummy(id = id, discordGuildId = guildId)

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

    @Test
    fun `should find trigger associated audio by trigger id`() {
        val id = UUID.randomUUID().toString()
        val audioId = UUID.randomUUID().toString()

        createDummy(id = id)
        createAudioDummy(id = audioId, triggerId = id)

        val expectedBody = """
            {
                "id": "$audioId",
                "triggerId": "$id",
                "guildId": "94101459",
                "file": "$audioId.mp3"
            }
        """.uglifyJson()

        request("/api/v1/trigger/$id/audio").andExpect {
            assertEquals(200, it.response.status)
            assertEquals(expectedBody, it.response.contentAsString)
        }
    }

    @Test
    fun `should not find a trigger audio by trigger id`() {
        request("/api/v1/trigger/1b96c970-1a70-40a4-9dec-b32ba8408750/audio").andExpect {
            assertEquals(404, it.response.status)
        }
    }

}
