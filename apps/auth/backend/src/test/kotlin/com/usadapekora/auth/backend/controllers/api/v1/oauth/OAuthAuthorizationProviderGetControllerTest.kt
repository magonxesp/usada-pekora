package com.usadapekora.auth.backend.controllers.api.v1.oauth

import com.usadapekora.auth.backend.SpringBootHttpTestCase
import kotlin.test.Test

class OAuthAuthorizationProviderGetControllerTest : SpringBootHttpTestCase() {

    @Test
    fun `it should return the discord provider url`() {
        webClient.get()
            .uri("/api/v1/oauth/provider/discord/authorize")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
    }

    @Test
    fun `it should return a 404 status with unknown provider`() {
        webClient.get()
            .uri("/api/v1/oauth/provider/unknown/authorize")
            .exchange()
            .expectStatus().isNotFound
    }

}
