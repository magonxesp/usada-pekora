package com.usadapekora.bot.backend.controller.api.v1.trigger.text

import com.usadapekora.bot.backend.controller.api.v1.trigger.TriggerControllerTest
import com.usadapekora.bot.backend.uglifyJson
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerTextResponseDeleteApiControllerTest : TriggerControllerTest() {

    private fun request(url: String)
        = mockMvc.perform(
            MockMvcRequestBuilders.delete(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

    @Test
    fun `should delete a trigger text response`() {
        val id = UUID.randomUUID().toString()
        createTextDummy(id = id)

        request("/api/v1/trigger/response/text/$id").andExpect {
            assertEquals(HttpStatus.OK.value(), it.response.status)
        }
    }

    @Test
    fun `should not delete a not existing trigger text response`() {
        val id = UUID.randomUUID().toString()

        request("/api/v1/trigger/response/text/$id").andExpect {
            assertEquals(HttpStatus.BAD_REQUEST.value(), it.response.status)
        }
    }

}
