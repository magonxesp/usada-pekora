package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.backend.uglifyJson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerTextPutV1Test : TriggerTest() {

    @Test
    fun `should update a trigger text response`() = withTestApplication {
        val id = UUID.randomUUID().toString()
        createTextDummy(id = id)

        val request = """
            {
                "content": "Konpeko Konpeko Konpeko!"
            }
        """.uglifyJson()

        val putResponse = client.put("/api/v1/trigger/response/text/$id") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(request)
        }

        assertEquals(HttpStatusCode.OK, putResponse.status)

        val response = """
            {
                "id": "$id",
                "content": "Konpeko Konpeko Konpeko!",
                "type": "text"
            }
        """.uglifyJson()

        val getResponse = client.get("/api/v1/trigger/response/text/$id") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, getResponse.status)
        assertEquals(response, getResponse.bodyAsText())
    }

    @Test
    fun `should not update a not existing trigger text response`() = withTestApplication {
        val id = UUID.randomUUID().toString()

        val request = """
            {
                "content": "Konpeko Konpeko Konpeko!"
            }
        """.uglifyJson()

        val response = client.put("/api/v1/trigger/response/text/$id") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(request)
        }

        assertEquals(HttpStatusCode.NotFound, response.status)
    }

}
