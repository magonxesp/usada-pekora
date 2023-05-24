package com.usadapekora.auth.backend.controllers.api.v1.oauth

import com.usadapekora.auth.application.oauth.OAuthAuthorizationProviderAuthorizationHandler
import com.usadapekora.auth.domain.oauth.OAuthProviderError
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/v1/oauth/provider")
class OAuthAuthorizationProviderPostController {

    private val callbackHandler: OAuthAuthorizationProviderAuthorizationHandler by inject(OAuthAuthorizationProviderAuthorizationHandler::class.java)

    fun mapHttpStatusError(error: OAuthProviderError) = when (error) {
        is OAuthProviderError.NotAvailable -> HttpStatus.BAD_REQUEST
        is OAuthProviderError.CallbackError -> HttpStatus.INTERNAL_SERVER_ERROR
    }

    @PostMapping("/{provider}/handle-authorization", produces = ["text/plain"])
    suspend fun login(@PathVariable("provider") provider: String, @RequestParam("code") code: Optional<String>): String
        = code.getOrNull()?.let {
            callbackHandler.handle(provider, it)
                .onLeft { left -> throw ResponseStatusException(mapHttpStatusError(left), left.message) }
                .getOrNull()!!.value
        } ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "The authorization code provided by the oauth provider is required")

}
