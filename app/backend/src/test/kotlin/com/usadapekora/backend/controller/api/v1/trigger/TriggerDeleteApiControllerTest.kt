package com.usadapekora.backend.controller.api.v1.trigger

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerDeleteApiControllerTest : TriggerControllerTest() {

    private fun request(url: String)
        = mockMvc.perform(
        MockMvcRequestBuilders.delete(url)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
    )

    @Test
    fun `should delete trigger by id`() {
        val id = UUID.randomUUID().toString()
        createDummy(id = id)

        request("/api/v1/trigger/$id").andExpect {
            assertEquals(200, it.response.status)
        }
    }

}
