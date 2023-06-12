package com.usadapekora.shared.infrastructure.auth0

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.JwkProviderBuilder
import com.usadapekora.shared.jwtIssuer
import java.util.concurrent.TimeUnit

class Auth0JwkProvider {

    fun provider(): JwkProvider
        = JwkProviderBuilder(jwtIssuer)
            .cached(10, 24, TimeUnit.HOURS)
            .rateLimited(10, 1, TimeUnit.MINUTES)
            .build()

}
