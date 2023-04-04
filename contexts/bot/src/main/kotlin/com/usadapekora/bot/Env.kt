package com.usadapekora.bot

import io.github.cdimascio.dotenv.dotenv
import java.io.File

private val dotenv = dotenv {
    filename = ".env"
    ignoreIfMissing = true
}

private fun env(key: String, defaultValue: String = ""): String {
    val file = dotenv["${key}_FILE"].takeIf { it != null }?.let {
        File(it)
    }

    if (file != null && file.exists()) {
        return file.readText().trim()
    }

    return dotenv[key] ?: defaultValue
}

val appEnv = env("APP_ENV", "develop")
val strapiBaseUrl = env("STRAPI_BASE_URL").removeSuffix("/")
val strapiToken = env("STRAPI_TOKEN")
val discordBotToken = env("DISCORD_BOT_TOKEN")
val backendBaseUrl = env("BACKEND_BASE_URL", "http://localhost:8080").removeSuffix("/")
val youtubeChannelId = env("YOUTUBE_CHANNEL_ID", "UC1DCedRgGHBdm81E1llLhOQ")
val mongoConnectionUrl = env("MONGODB_URL", "mongodb://example:example@localhost:27017")
val mongoDatabase = env("MONGODB_DATABASE", "usada_pekora")
val redisHost = env("REDIS_HOST", "localhost")
val redisPort = env("REDIS_PORT", "6379").toInt()
val storageDirPath = env("STORAGE_DIR_PATH", "storage")
