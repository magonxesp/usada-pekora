package com.usadapekora.bot.infraestructure.guild.persistence.discord

import com.usadapekora.bot.domain.guild.Guild
import com.usadapekora.bot.domain.guild.GuildProviderRepository
import com.usadapekora.shared.domain.user.User
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.util.UUID

class DiscordGuildProviderRepository(private val token: String) : GuildProviderRepository {
    private val jsonDecoder = Json { ignoreUnknownKeys = true }

    override fun findAll(userId: User.UserId): Array<Guild> {
        val client = HttpClient(CIO)
        val guilds = runBlocking {
            try {
                val response = client.get("https://discord.com/api/v10/users/@me/guilds") {
                    header("Authorization", "Bearer $token")
                }

                jsonDecoder.decodeFromString<List<DiscordGuild>>(response.bodyAsText())
            } catch (e: Exception) {
                listOf()
            }
        }

        client.close()

        return guilds.map { guild ->
            Guild.fromPrimitives(
                id = UUID.randomUUID().toString(),
                name = guild.name,
                iconUrl = guild.icon?.let {
                    "https://cdn.discordapp.com/icons/${guild.id}/$it.png"
                } ?: "",
                providerId = guild.id,
                provider = "discord"
            )
        }.toTypedArray()
    }
}
