package com.usadapekora.bot.backend.controller.api.v1.trigger.text

import com.usadapekora.bot.backend.controller.api.v1.trigger.TriggerControllerTest
import com.usadapekora.bot.backend.uglifyJson
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerTextResponsePutApiControllerTest : TriggerControllerTest() {

    private fun putRequest(url: String, body: String)
        = mockMvc.perform(
            MockMvcRequestBuilders.put(url)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

    private fun getRequest(url: String)
        = mockMvc.perform(
        MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
    )

    @Test
    fun `should update a trigger text response`() {
        val id = UUID.randomUUID().toString()
        createTextDummy(id = id)

        val request = """
            {
                "content": "Konpeko Konpeko Konpeko!"
            }
        """.uglifyJson()

        putRequest("/api/v1/trigger/response/text/$id", request).andExpect {
            assertEquals(HttpStatus.OK.value(), it.response.status)
        }

        val response = """
            {
                "id": "$id",
                "content": "Konpeko Konpeko Konpeko!",
                "type": "text"
            }
        """.uglifyJson()

        getRequest("/api/v1/trigger/response/text/$id").andExpect {
            assertEquals(HttpStatus.OK.value(), it.response.status)
            assertEquals(response, it.response.contentAsString)
        }
    }

    @Test
    fun `should not update a not existing trigger text response`() {
        val id = UUID.randomUUID().toString()

        val request = """
            {
                "content": "Konpeko Konpeko Konpeko!"
            }
        """.uglifyJson()

        putRequest("/api/v1/trigger/response/text/$id", request).andExpect {
            assertEquals(HttpStatus.BAD_REQUEST.value(), it.response.status)
        }
    }

}
