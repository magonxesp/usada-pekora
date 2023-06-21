package com.usadapekora.auth.infrastructure.oauth.discord

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.auth.discordClientId
import com.usadapekora.auth.discordClientSecret
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationProvider
import com.usadapekora.auth.domain.oauth.OAuthProvider
import com.usadapekora.auth.domain.oauth.OAuthProviderError
import com.usadapekora.auth.domain.oauth.OAuthUser
import com.usadapekora.auth.oAuthProviderRedirectUrl
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import java.net.URLEncoder

class DiscordOAuthProvider : OAuthAuthorizationProvider {

    private val redirectUri = oAuthProviderRedirectUrl.replace("%provider%", OAuthProvider.DISCORD.value)
    private val jsonDecoder = Json { ignoreUnknownKeys = true }

    override fun authorizeUrl(): String {
        val params = mapOf(
            "response_type" to "code",
            "client_id" to discordClientId,
            "redirect_uri" to redirectUri,
            "scope" to "identify guilds"
        )

        val urlEncodedParams = params.map { "${it.key}=${URLEncoder.encode(it.value, "UTF-8")}" }
            .joinToString("&")

        return "https://discord.com/oauth2/authorize?$urlEncodedParams"
    }

    private suspend fun fetchAccessToken(code: String): Either<Throwable, DiscordTokenResponse> {
        val client = HttpClient(CIO)
        val token = Either.catch {
            val response = client.post("https://discord.com/api/v10/oauth2/token") {
                setBody(
                    mapOf(
                        "client_id" to discordClientId,
                        "client_secret" to discordClientSecret,
                        "grant_type" to "authorization_code",
                        "code" to code,
                        "redirect_uri" to redirectUri
                    ).let {
                        it.map { "${it.key}=${it.value}" }
                            .joinToString("&")
                    }
                )
                contentType(ContentType.Application.FormUrlEncoded)
            }

            jsonDecoder.decodeFromString<DiscordTokenResponse>(response.bodyAsText())
        }

        client.close()
        return token
    }

    private suspend fun fetchUser(token: DiscordTokenResponse): Either<Throwable, DiscordUser> {
        val client = HttpClient(CIO)
        val user = Either.catch {
            val response = client.get("https://discord.com/api/v10/users/@me") {
                header("Authorization", "Bearer ${token.accessToken}")
            }

            jsonDecoder.decodeFromString<DiscordUser>(response.bodyAsText())
        }

        client.close()
        return user
    }

    override suspend fun handleCallback(code: String): Either<OAuthProviderError.CallbackError, OAuthUser> {
        val token = fetchAccessToken(code).let {
            if (it.isLeft()) return OAuthProviderError.CallbackError(it.leftOrNull()!!.message).left()
            it.getOrNull()!!
        }

        val user = fetchUser(token).let {
            if (it.isLeft()) return OAuthProviderError.CallbackError(it.leftOrNull()!!.message).left()
            it.getOrNull()!!
        }

        return OAuthUser(
            id = user.id,
            avatar = user.avatarUrl,
            name = user.username,
            token = token.accessToken
        ).right()
    }

}
