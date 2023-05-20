package com.usadapekora.auth.backend.controllers.api

import com.usadapekora.auth.application.AuthorizationUrlProvider
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController("/api/v1")
class OAuthAuthorizationProviderController {

    private val urlProvider: AuthorizationUrlProvider by inject(AuthorizationUrlProvider::class.java)

    @GetMapping("/oauth/provider/{provider}")
    suspend fun login(@PathVariable("provider") provider: String)
        = urlProvider.getUrl(provider).let {
            if (it.isLeft()) throw ResponseStatusException(HttpStatus.NOT_FOUND, it.leftOrNull()!!.message)
            it.getOrNull()!!
        }

}
