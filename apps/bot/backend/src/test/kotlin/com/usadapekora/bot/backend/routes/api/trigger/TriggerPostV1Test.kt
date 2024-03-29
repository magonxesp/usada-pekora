package com.usadapekora.bot.backend.routes.api.trigger

import io.ktor.client.request.*
import io.ktor.http.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerPostV1Test : TriggerTest() {

    @Test
    fun `should create a trigger making a POST request`() = withTestApplication {
        val audioId = UUID.randomUUID().toString()
        createAudioDummy(id = audioId)

        val requestBody = """
            {
                "id": "${UUID.randomUUID()}",
                "title": "Dummy trigger",
                "input": "peko",
                "compare": "in",
                "guildId": "2fe3367b-61a8-402c-9df4-20561b058635",
                "responseAudioId": "$audioId"
            }
        """.trimIndent()

        val response = client.post("/api/v1/trigger") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(requestBody)
        }

        assertEquals(HttpStatusCode.Created, response.status)
    }

    @Test
    fun `should not create a duplicated trigger making a POST request`() = withTestApplication {
        val audioId = UUID.randomUUID().toString()
        createAudioDummy(id = audioId)

        val requestBody = """
            {
                "id": "${UUID.randomUUID()}",
                "title": "Dummy trigger",
                "input": "peko",
                "compare": "in",
                "guildId": "2fe3367b-61a8-402c-9df4-20561b058635",
                "responseAudioId": "$audioId"
            }
        """.trimIndent()

        var response = client.post("/api/v1/trigger") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(requestBody)
        }

        assertEquals(HttpStatusCode.Created, response.status)

        response = client.post("/api/v1/trigger") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(requestBody)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `should not create a trigger with invalid body`() = withTestApplication {
        val requestBody = """
            {
                "id": "${UUID.randomUUID()}",
                "input": "peko",
                "compare": "abc",
                "outputText": "It's a me pekora"
            }
        """.trimIndent()

        val response = client.post("/api/v1/trigger") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(requestBody)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

}
