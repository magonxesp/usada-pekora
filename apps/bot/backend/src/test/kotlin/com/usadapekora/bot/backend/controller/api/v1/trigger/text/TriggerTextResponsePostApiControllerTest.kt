package com.usadapekora.bot.backend.controller.api.v1.trigger.text

import com.usadapekora.bot.backend.controller.api.v1.trigger.TriggerControllerTest
import com.usadapekora.bot.backend.uglifyJson
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerTextResponsePostApiControllerTest : TriggerControllerTest() {

    private fun request(url: String, body: String)
        = mockMvc.perform(
            MockMvcRequestBuilders.post(url)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

    @Test
    fun `should create a trigger text response`() {
        val id = UUID.randomUUID().toString()

        val request = """
            {
                "id": "$id",
                "content": "Konpeko Konpeko Konpeko! 3rd Generation, Usada Pekora peko! Almond...almond....!!",
                "type": "text"
            }
        """.uglifyJson()

        request("/api/v1/trigger/response/text", request).andExpect {
            assertEquals(HttpStatus.CREATED.value(), it.response.status)
        }
    }

    @Test
    fun `should not crate an existing trigger text response`() {
        val id = UUID.randomUUID().toString()

        val request = """
            {
                "id": "$id",
                "content": "Konpeko Konpeko Konpeko! 3rd Generation, Usada Pekora peko! Almond...almond....!!",
                "type": "text"
            }
        """.uglifyJson()

        request("/api/v1/trigger/response/text", request).andExpect {
            assertEquals(HttpStatus.CREATED.value(), it.response.status)
        }

        request("/api/v1/trigger/response/text", request).andExpect {
            assertEquals(HttpStatus.BAD_REQUEST.value(), it.response.status)
        }
    }

}
