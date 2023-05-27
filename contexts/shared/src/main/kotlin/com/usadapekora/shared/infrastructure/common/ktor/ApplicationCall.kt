package com.usadapekora.shared.infrastructure.common.ktor

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun ApplicationCall.respondError(httpStatusCode: HttpStatusCode, message: String) {
    respond(
        httpStatusCode,
        ResponseError(message),
    )
}
