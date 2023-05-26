package com.usadapekora.auth.backend.routes.api

import com.usadapekora.auth.backend.routes.api.oauth.authorizationProviderRoutesV1
import io.ktor.server.routing.*

fun Route.apiRoutesV1() {
    authorizationProviderRoutesV1()
}
