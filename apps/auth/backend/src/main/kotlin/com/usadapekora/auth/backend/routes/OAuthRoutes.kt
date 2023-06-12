package com.usadapekora.auth.backend.routes

import com.usadapekora.auth.application.jwt.AccessJwtIssuer
import com.usadapekora.auth.domain.jwt.JwtError
import com.usadapekora.shared.infrastructure.ktor.respondError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.oauthRoutes() {
    route("/oauth") {
        post("/token") {
            val code = call.request.queryParameters["code"]
                ?: return@post call.respondError(HttpStatusCode.BadRequest, "The code query parameter is missing")

            val accessJwtIssuer: AccessJwtIssuer by inject(AccessJwtIssuer::class.java)

            accessJwtIssuer.issue(code)
                .onLeft {
                    call.respondError(
                        httpStatusCode = when(it) {
                            is JwtError.CodeNotFound -> HttpStatusCode.BadRequest
                            else -> HttpStatusCode.InternalServerError
                        },
                        message = it.message ?: ""
                    )
                }
                .onRight { call.respond(it) }
        }
    }
}
