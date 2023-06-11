package com.usadapekora.bot.backend.routes.api.trigger

import com.usadapekora.bot.backend.uglifyJson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import java.util.*
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals


class TriggerGetV1Test : TriggerTest() {

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
                "discordGuildId": "94101459"
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
        createDummy(id = id, discordGuildId = guildId, responseAudioId = audioId)

        val expectedBody = """
            {
                "triggers": [
                    {
                        "id": "$id",
                        "title": "Dummy trigger",
                        "input": "peko",
                        "compare": "in",
                        "responseTextId": null,
                        "responseAudioId": "$audioId",
                        "discordGuildId": "$guildId"
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
                "triggers": []
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
                "guildId": "94101459",
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
