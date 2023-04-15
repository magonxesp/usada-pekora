package com.usadapekora.bot.backend.controller.api.v1.trigger.text

import com.usadapekora.bot.backend.controller.api.v1.trigger.TriggerControllerTest
import com.usadapekora.bot.backend.uglifyJson
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerTextResponseGetApiControllerTest : TriggerControllerTest() {

    private fun request(url: String)
        = mockMvc.perform(
            MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

    @Test
    fun `should find a trigger text response`() {
        val id = UUID.randomUUID().toString()
        createTextDummy(id = id)

        val response = """
            {
                "id": "$id",
                "content": "Konpeko Konpeko Konpeko! 3rd Generation, Usada Pekora peko! Almond...almond....!!",
                "type": "text"
            }
        """.uglifyJson()

        request("/api/v1/trigger/response/text/$id").andExpect {
            assertEquals(HttpStatus.OK.value(), it.response.status)
            assertEquals(response, it.response.contentAsString)
        }
    }

    @Test
    fun `should not find a not existing trigger text response`() {
        val id = UUID.randomUUID().toString()

        request("/api/v1/trigger/response/text/$id").andExpect {
            assertEquals(HttpStatus.NOT_FOUND.value(), it.response.status)
        }
    }

}
