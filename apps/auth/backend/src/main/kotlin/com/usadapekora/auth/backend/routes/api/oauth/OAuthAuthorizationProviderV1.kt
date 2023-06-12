package com.usadapekora.auth.backend.routes.api.oauth

import com.usadapekora.auth.application.oauth.OAuthAuthorizationProviderAuthorizationHandler
import com.usadapekora.auth.application.oauth.OAuthAuthorizationProviderAuthorizeUrlFactory
import com.usadapekora.shared.infrastructure.ktor.respondError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Route.authorizationProviderRoutesV1() {
    route("/api/v1/oauth/provider/{provider}") {
        get("/authorize") {
            val urlProvider: OAuthAuthorizationProviderAuthorizeUrlFactory by inject(
                OAuthAuthorizationProviderAuthorizeUrlFactory::class.java
            )

            val provider = call.parameters["provider"]!!

            urlProvider.getUrl(provider)
                .onLeft { call.respondError(HttpStatusCode.NotFound, it.message ?: "") }
                .onRight { call.respond(it) }
        }
        post("/handle-authorization"){
            val callbackHandler: OAuthAuthorizationProviderAuthorizationHandler by inject(
                OAuthAuthorizationProviderAuthorizationHandler::class.java
            )

            val provider = call.parameters["provider"]!!
            val code = call.request.queryParameters["code"]

            if (code.isNullOrBlank()) {
                return@post call.respondError(HttpStatusCode.BadRequest, "The authorization code provided by the oauth provider is required")
            }

            callbackHandler.handle(provider, code)
                .onLeft { call.respondError(HttpStatusCode.InternalServerError, it.message ?: "") }
                .onRight { call.respond(it.value) }
        }
    }
}
