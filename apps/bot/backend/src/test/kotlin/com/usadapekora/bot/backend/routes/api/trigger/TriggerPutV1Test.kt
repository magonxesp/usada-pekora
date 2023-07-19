package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.backend.uglifyJson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerPutV1Test : TriggerTest() {

    @Test
    fun `should update trigger by id`() = withTestApplication {
        val id = UUID.randomUUID().toString()
        val audioId = UUID.randomUUID().toString()
        val textId = UUID.randomUUID().toString()
        createAudioDummy(id = audioId)
        createDummy(id = id, responseAudioId = audioId)
        createTextDummy(id = textId)

        val requestBody = """
            {
                "input": "pekopeko",
                "compare": "in",
                "responseTextId": "$textId",
                "guildId": "2fe3367b-61a8-402c-9df4-20561b058635"
            }
        """.uglifyJson()

        var response = client.put("/api/v1/trigger/$id") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(requestBody)
        }

        assertEquals(HttpStatusCode.OK, response.status)

        val expected = """
            {
                "id": "$id",
                "title": "Dummy trigger",
                "input": "pekopeko",
                "compare": "in",
                "responseTextId": "$textId",
                "responseAudioId": null,
                "guildId": "2fe3367b-61a8-402c-9df4-20561b058635"
            }
        """.uglifyJson()

        response = client.get("/api/v1/trigger/$id") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expected, response.bodyAsText())
    }

}
