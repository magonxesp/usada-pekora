package com.usadapekora.auth.infrastructure.oauth.discord

import kotlinx.serialization.Serializable

@Serializable
class DiscordUser(
    val id: String,
    val username: String,
    val avatar: String,
) {
    val avatarUrl: String
        get() = "https://cdn.discordapp.com/avatars/${id}/${avatar}.png"
}
