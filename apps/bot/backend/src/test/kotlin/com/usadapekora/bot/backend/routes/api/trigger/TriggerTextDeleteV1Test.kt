package com.usadapekora.bot.backend.routes.api.trigger

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TriggerTextDeleteV1Test : TriggerTest() {

    @Test
    fun `should delete a trigger text response`() = testApplication {
        val id = UUID.randomUUID().toString()
        createTextDummy(id = id)

        val response = client.delete("/api/v1/trigger/response/text/$id") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `should not delete a not existing trigger text response`()= testApplication {
        val id = UUID.randomUUID().toString()

        val response = client.delete("/api/v1/trigger/response/text/$id") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.NotFound, response.status)
    }

}
