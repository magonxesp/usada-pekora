package com.usadapekora.backend.controller.api.v1.trigger

import com.usadapekora.backend.uglifyJson
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerPutApiControllerTest : TriggerControllerTest() {

    private fun request(url: String, requestBody: String)
        = mockMvc.perform(
            MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
    )

    private fun getRequest(url: String)
        = mockMvc.perform(
        MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
    )

    @Test
    fun `should update trigger by id`() {
        val id = UUID.randomUUID().toString()
        createDummy(id = id)

        val requestBody = """
            {
                "input": "pekopeko",
                "compare": "in",
                "outputText": "Konpeko Konpeko Konpeko! 3rd Generation, Usada Pekora peko! Almond...almond....!!",
                "discordGuildId": "94101459"
            }
        """.uglifyJson()

        request("/api/v1/trigger/$id", requestBody).andExpect {
            assertEquals(200, it.response.status)
        }

        val expected = """
            {
                "id": "$id",
                "title": "Dummy trigger",
                "input": "pekopeko",
                "compare": "in",
                "outputText": "Konpeko Konpeko Konpeko! 3rd Generation, Usada Pekora peko! Almond...almond....!!",
                "discordGuildId": "94101459"
            }
        """.uglifyJson()

        getRequest("/api/v1/trigger/$id").andExpect {
            assertEquals(200, it.response.status)
            assertEquals(expected, it.response.contentAsString)
        }
    }

}
