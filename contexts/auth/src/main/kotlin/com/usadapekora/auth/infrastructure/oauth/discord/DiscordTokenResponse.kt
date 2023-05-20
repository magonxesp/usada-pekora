package com.usadapekora.auth.infrastructure.oauth.discord

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
class DiscordTokenResponse @OptIn(ExperimentalSerializationApi::class) constructor(
    @JsonNames("access_token")
    val accessToken: String,
    @JsonNames("token_type")
    val tokenType: String,
    @JsonNames("expires_in")
    val expiresIn: Long,
    @JsonNames("refresh_token")
    val refreshToken: String,
    @JsonNames("scope")
    val scope: String,
)
