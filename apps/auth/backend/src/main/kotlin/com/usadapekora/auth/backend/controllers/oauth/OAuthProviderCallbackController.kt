package com.usadapekora.auth.backend.controllers.oauth

import com.usadapekora.auth.application.oauth.AuthorizationCallbackHandler
import org.koin.java.KoinJavaComponent.inject
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.Optional

@RestController
@RequestMapping("/oauth")
class OAuthProviderCallbackController {

    private val callbackHandler: AuthorizationCallbackHandler by inject(AuthorizationCallbackHandler::class.java)

    @GetMapping("/{provider}/callback")
    suspend fun callback(@PathVariable("provider") provider: String, @RequestParam("code") code: Optional<String>) {
        callbackHandler.handle(provider, code.orElse("")).let {
            if (it.isLeft()) throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, it.leftOrNull()!!.message)
        }
        // TODO redirect to error page
    }

}
