package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.backend.uglifyJson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerTextGetV1Test : TriggerTest() {

    @Test
    fun `should find a trigger text response`() = withTestApplication {
        val id = UUID.randomUUID().toString()
        createTextDummy(id = id)

        val expected = """
            {
                "id": "$id",
                "content": "Konpeko Konpeko Konpeko! 3rd Generation, Usada Pekora peko! Almond...almond....!!",
                "type": "text"
            }
        """.uglifyJson()

        val response = client.get("/api/v1/trigger/response/text/$id") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expected, response.bodyAsText())
    }

    @Test
    fun `should not find a not existing trigger text response`() = withTestApplication {
        val id = UUID.randomUUID().toString()

        val response = client.get("/api/v1/trigger/response/text/$id") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.NotFound, response.status)
    }

}
