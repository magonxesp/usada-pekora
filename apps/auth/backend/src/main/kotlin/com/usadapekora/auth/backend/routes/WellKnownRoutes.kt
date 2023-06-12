package com.usadapekora.auth.backend.routes

import com.usadapekora.auth.application.jwt.SignatureJwkIssuer
import com.usadapekora.shared.infrastructure.ktor.respondError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.wellKnown() {
    get("/.well-known/jwks.json") {
        val issuer: SignatureJwkIssuer by inject(SignatureJwkIssuer::class.java)

        issuer.issue()
            .onLeft { call.respondError(HttpStatusCode.InternalServerError, it.message ?: "") }
            .onRight {
                val keys = "{\"keys\":[$it]}"
                call.respondText(keys, ContentType.Application.Json, HttpStatusCode.OK)
            }
    }
}
