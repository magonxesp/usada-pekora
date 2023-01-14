package com.usadapekora.backend.controller.api.v1.trigger

import com.usadapekora.backend.SpringBootHttpTestCase
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerPostApiControllerTest : SpringBootHttpTestCase() {

    private fun requestTest(requestBody: String)
        = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/trigger")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )

    @Test
    fun `should create a trigger making a POST request`() {
        val requestBody = """
            {
                "id": "${UUID.randomUUID()}",
                "input": "peko",
                "compare": "in",
                "outputText": "It's a me pekora",
                "discordGuildId": "94101459"
            }
        """.trimIndent()

        requestTest(requestBody).andExpect {
            assertEquals(HttpStatus.CREATED.value(), it.response.status)
        }
    }

    @Test
    fun `should not create a duplicated trigger making a POST request`() {
        val requestBody = """
            {
                "id": "${UUID.randomUUID()}",
                "input": "peko",
                "compare": "in",
                "outputText": "It's a me pekora",
                "discordGuildId": "94101459"
            }
        """.trimIndent()

        requestTest(requestBody).andExpect {
            assertEquals(HttpStatus.CREATED.value(), it.response.status)
        }

        requestTest(requestBody).andExpect {
            assertEquals(HttpStatus.BAD_REQUEST.value(), it.response.status)
        }
    }

    @Test
    fun `should not create a trigger with invalid body`() {
        val requestBody = """
            {
                "id": "${UUID.randomUUID()}",
                "input": "peko",
                "compare": "abc",
                "outputText": "It's a me pekora"
            }
        """.trimIndent()

        requestTest(requestBody).andExpect {
            assertEquals(HttpStatus.BAD_REQUEST.value(), it.response.status)
        }
    }

}
