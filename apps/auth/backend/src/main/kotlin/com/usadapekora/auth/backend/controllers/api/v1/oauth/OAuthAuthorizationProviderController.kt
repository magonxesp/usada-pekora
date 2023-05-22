package com.usadapekora.auth.backend.controllers.api.v1.oauth

import com.usadapekora.auth.application.oauth.OAuthAuthorizationProviderAuthorizeUrlFactory
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1")
class OAuthAuthorizationProviderController {

    private val urlProvider: OAuthAuthorizationProviderAuthorizeUrlFactory by inject(OAuthAuthorizationProviderAuthorizeUrlFactory::class.java)

    @GetMapping("/oauth/provider/authorize/{provider}")
    suspend fun login(@PathVariable("provider") provider: String): String
        = urlProvider.getUrl(provider).let {
            if (it.isLeft()) throw ResponseStatusException(HttpStatus.NOT_FOUND, it.leftOrNull()!!.message)
            it.getOrNull()!!
        }

}
