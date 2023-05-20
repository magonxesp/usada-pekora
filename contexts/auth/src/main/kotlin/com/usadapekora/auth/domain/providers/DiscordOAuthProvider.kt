package com.usadapekora.auth.domain.providers

import com.usadapekora.auth.discordClientId
import com.usadapekora.auth.domain.OAuthAuthorizationProvider
import java.net.URLEncoder

class DiscordOAuthProvider : OAuthAuthorizationProvider {

    override fun authorizeUrl(): String {
        val params = mapOf(
            "response_type" to "code",
            "client_id" to discordClientId,
            "redirect_uri" to "http://localhost:3000/login",
            "scope" to "identify guilds"
        )

        val urlEncodedParams = params.map { "${it.key}=${URLEncoder.encode(it.value, "UTF-8")}" }
            .joinToString("&")

        return "https://discord.com/oauth2/authorize?$urlEncodedParams"
    }
}
