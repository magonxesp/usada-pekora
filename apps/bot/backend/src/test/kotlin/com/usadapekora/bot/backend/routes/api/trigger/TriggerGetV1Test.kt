package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.backend.uglifyJson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.util.*
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals


class TriggerGetV1Test : TriggerTest() {

    private val builtInTriggersResponse = """
        {
            "id": "84c6a7f1-1b1d-4f59-a3d3-13d0cb0db65d",
            "title": "It’s a me, Pekora!",
            "input": "peko",
            "compare": "in",
            "responseTextId": "a1b5eb1e-fc9d-4d03-a732-291fd57599b6",
            "responseAudioId": "cff9451d-6c73-4c79-8af3-52bc4102cceb",
            "guildId": null
        },
        {
            "id": "a2b1e1df-921d-49c3-9238-86cfafd0f47d",
            "title": "HA↗HA↘HA↗HA↘HA↗HA↘",
            "input": "( +)(([HhJj]+)([Aa]+)){3,}( +)?|^(([HhJj]+)([Aa]+)){3,}$",
            "compare": "pattern",
            "responseTextId": "ce4c2b3d-51a2-4133-a127-3864b50fb471",
            "responseAudioId": "ed0166ac-b184-4439-b852-69a634e63245",
            "guildId": null
        }
    """.trimIndent()

    @Test
    fun `should find a trigger by id`() = withTestApplication {
        val id = UUID.randomUUID().toString()
        val audioId = UUID.randomUUID().toString()
        createAudioDummy(id = audioId)
        createDummy(id = id, responseAudioId = audioId)

        val expectedBody = """
            {
                "id": "$id",
                "title": "Dummy trigger",
                "input": "peko",
                "compare": "in",
                "responseTextId": null,
                "responseAudioId": "$audioId",
                "guildId": "2fe3367b-61a8-402c-9df4-20561b058635"
            }
        """.uglifyJson()

        val response = client.get("/api/v1/trigger/$id") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expectedBody, response.bodyAsText())
    }

    @Test
    fun `should find a built-in trigger by id`() = withTestApplication {
        val expectedBody = """
            {
                "id": "84c6a7f1-1b1d-4f59-a3d3-13d0cb0db65d",
                "title": "It’s a me, Pekora!",
                "input": "peko",
                "compare": "in",
                "responseTextId": "a1b5eb1e-fc9d-4d03-a732-291fd57599b6",
                "responseAudioId": "cff9451d-6c73-4c79-8af3-52bc4102cceb",
                "guildId": null
            }
        """.uglifyJson()

        val response = client.get("/api/v1/trigger/84c6a7f1-1b1d-4f59-a3d3-13d0cb0db65d") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expectedBody, response.bodyAsText())
    }

    @Test
    fun `should not find a trigger by id`() = withTestApplication {
        val response = client.get("/api/v1/trigger/e322b3ac-2d30-4eff-afdc-3504f66ac4ba") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Test
    fun `should find a triggers by discord guild id`() = withTestApplication{
        val id = UUID.randomUUID().toString()
        val guildId = Random.nextLong(100000000, 999999999).toString()
        val audioId = UUID.randomUUID().toString()

        createAudioDummy(id = audioId)
        createDummy(id = id, guildId = guildId, responseAudioId = audioId)

        val expectedBody = """
            {
                "triggers": [
                    $builtInTriggersResponse,
                    {
                        "id": "$id",
                        "title": "Dummy trigger",
                        "input": "peko",
                        "compare": "in",
                        "responseTextId": null,
                        "responseAudioId": "$audioId",
                        "guildId": "$guildId"
                    }
                ]
            }
        """.uglifyJson()

        val response = client.get("/api/v1/trigger/guild/$guildId") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expectedBody, response.bodyAsText())
    }

    @Test
    fun `should not find a triggers by discord guild id`() = withTestApplication {
        val guildId = Random.nextLong(100000000, 999999999).toString()
        val expectedBody = """
            {
                "triggers": [
                    $builtInTriggersResponse
                ]
            }
        """.uglifyJson()

        val response = client.get("/api/v1/trigger/guild/$guildId") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expectedBody, response.bodyAsText())
    }

    @Test
    fun `should find trigger associated audio by trigger id`() = withTestApplication {
        val id = UUID.randomUUID().toString()
        val audioId = UUID.randomUUID().toString()

        createAudioDummy(id = audioId, triggerId = id)
        createDummy(id = id, responseAudioId = audioId)

        val expectedBody = """
            {
                "id": "$audioId",
                "triggerId": "$id",
                "guildId": "2fe3367b-61a8-402c-9df4-20561b058635",
                "file": "assets_audio_Its_me_pekora.mp3"
            }
        """.uglifyJson()

        val response = client.get("/api/v1/trigger/$id/audio") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expectedBody, response.bodyAsText())
    }

    @Test
    fun `should not find a trigger audio by trigger id`() = withTestApplication {
        val response = client.get("/api/v1/trigger/1b96c970-1a70-40a4-9dec-b32ba8408750/audio") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.NotFound, response.status)
    }
}
