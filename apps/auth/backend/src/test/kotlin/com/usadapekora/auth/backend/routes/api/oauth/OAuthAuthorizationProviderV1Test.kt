package com.usadapekora.auth.backend.routes.api.oauth

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class OAuthAuthorizationProviderV1Test {

    @Test
    fun `it should return the discord provider url`() = testApplication {
        val response = client.get("/api/v1/oauth/provider/discord/authorize")
        assertEquals(HttpStatusCode.OK, response.status)
        assertFalse(response.bodyAsText().isBlank())
    }

    @Test
    fun `it should return a 404 status with unknown provider`() = testApplication {
        val response = client.get("/api/v1/oauth/provider/unknown/authorize")
        assertEquals(HttpStatusCode.NotFound, response.status)
    }

}
