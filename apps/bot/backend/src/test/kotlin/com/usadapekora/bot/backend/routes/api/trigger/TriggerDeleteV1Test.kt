package com.usadapekora.bot.backend.routes.api.trigger

import io.ktor.client.request.*
import io.ktor.http.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerDeleteV1Test : TriggerTest() {

    @Test
    fun `should delete trigger by id`() = withTestApplication {
        val id = UUID.randomUUID().toString()
        val audioId = UUID.randomUUID().toString()

        createAudioDummy(id = audioId)
        createDummy(id = id, responseAudioId = audioId)

        val response = client.delete("/api/v1/trigger/$id") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }

}
