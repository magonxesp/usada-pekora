package com.usadapekora.shared.infrastructure.ktor

import com.usadapekora.shared.infrastructure.auth0.Auth0JwkProvider
import com.usadapekora.shared.jwtAudience
import com.usadapekora.shared.jwtIssuer
import io.ktor.http.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun JWTAuthenticationProvider.Config.defaultConfiguration() {
    realm = "com.usadapekora"

    verifier(Auth0JwkProvider().provider(), jwtIssuer) {
        acceptLeeway(3)
    }

    validate { credential ->
        if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
    }

    challenge { _, _ ->
        call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
    }
}
