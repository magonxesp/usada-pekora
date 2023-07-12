package com.usadapekora.shared

import io.github.cdimascio.dotenv.dotenv
import java.io.File

private val dotenv = dotenv {
    filename = ".env"
    ignoreIfMissing = true
}

fun env(key: String, defaultValue: String = ""): String {
    val file = dotenv["${key}_FILE"].takeIf { it != null }?.let {
        File(it)
    }

    if (file != null && file.exists()) {
        return file.readText().trim()
    }

    return dotenv[key] ?: defaultValue
}

val mongoConnectionUrl = env("MONGODB_URL", "mongodb://example:example@localhost:27017")
val mongoDatabase = env("MONGODB_DATABASE", "usada_pekora")
val redisHost = env("REDIS_HOST", "localhost")
val redisPort = env("REDIS_PORT", "6379").toInt()
val jwtIssuer = env("AUTH_JWT_ISSUER", "http://localhost:8081")
val jwtAudience = env("AUTH_JWT_AUDIENCE", "http://localhost")
val rabbitMqUrl = env("RABBITMQ_URL", "amqp://guest:guest@localhost:5672/%2f")
val discordBotToken = env("DISCORD_BOT_TOKEN")
val discordClientId = env("DISCORD_CLIENT_ID")
val discordClientSecret = env("DISCORD_CLIENT_SECRET")
val youtubeChannelId = env("YOUTUBE_CHANNEL_ID", "UC1DCedRgGHBdm81E1llLhOQ")
val storageDirPath = env("STORAGE_DIR_PATH", "storage")

// test environment variables
val testDiscordTextChannelId = env("TEST_DISCORD_CHANNEL_ID")
val testDiscordGuildId = env("TEST_DISCORD_GUILD_ID")
