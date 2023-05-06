package com.usadapekora.bot.backend.controller.api.v1.trigger

import com.usadapekora.bot.backend.uglifyJson
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
        val audioId = UUID.randomUUID().toString()
        createAudioDummy(id = audioId)
        createDummy(id = id, responseAudioId = audioId)

        val expectedBody = """
            {
                "id": "$id",
                "title": "Dummy trigger",
                "input": "peko",
                "compare": "in",
                "responseTextId": null,
                "responseAudioId": "$audioId",
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
        val audioId = UUID.randomUUID().toString()

        createAudioDummy(id = audioId)
        createDummy(id = id, discordGuildId = guildId, responseAudioId = audioId)

        val expectedBody = """
            {
                "triggers": [
                    {
                        "id": "$id",
                        "title": "Dummy trigger",
                        "input": "peko",
                        "compare": "in",
                        "responseTextId": null,
                        "responseAudioId": "$audioId",
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

        createAudioDummy(id = audioId, triggerId = id)
        createDummy(id = id, responseAudioId = audioId)

        val expectedBody = """
            {
                "id": "$audioId",
                "triggerId": "$id",
                "guildId": "94101459",
                "file": "assets_audio_Its_me_pekora.mp3"
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
