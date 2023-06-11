package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.backend.uglifyJson
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerTextPostV1Test : TriggerTest() {

    @Test
    fun `should create a trigger text response`() = withTestApplication {
        val id = UUID.randomUUID().toString()

        val request = """
            {
                "id": "$id",
                "content": "Konpeko Konpeko Konpeko! 3rd Generation, Usada Pekora peko! Almond...almond....!!",
                "type": "text"
            }
        """.uglifyJson()

        val response = client.post("/api/v1/trigger/response/text") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(request)
        }

        assertEquals(HttpStatusCode.Created, response.status)
    }

    @Test
    fun `should not crate an existing trigger text response`() = withTestApplication {
        val id = UUID.randomUUID().toString()

        val request = """
            {
                "id": "$id",
                "content": "Konpeko Konpeko Konpeko! 3rd Generation, Usada Pekora peko! Almond...almond....!!",
                "type": "text"
            }
        """.uglifyJson()

        var response = client.post("/api/v1/trigger/response/text") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(request)
        }

        assertEquals(HttpStatusCode.Created, response.status)

        response = client.post("/api/v1/trigger/response/text") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(request)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

}
