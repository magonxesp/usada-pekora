package com.usadapekora.context

import io.github.cdimascio.dotenv.dotenv

private val dotenv = dotenv {
    filename = ".env"
    ignoreIfMissing = true
}

val appEnv = dotenv.get("APP_ENV", "develop")
val strapiBaseUrl = dotenv.get("STRAPI_BASE_URL", "").removeSuffix("/")
val strapiToken = dotenv.get("STRAPI_TOKEN", "")
val discordBotToken = dotenv.get("DISCORD_BOT_TOKEN", "")
val backendBaseUrl = dotenv.get("BACKEND_BASE_URL", "http://localhost:8080").removeSuffix("/")
val youtubeChannelId = dotenv.get("YOUTUBE_CHANNEL_ID", "UC1DCedRgGHBdm81E1llLhOQ")
val mongoConnectionUrl = dotenv.get("MONGODB_URL", "")
val mongoDatabase = dotenv.get("MONGODB_DATABASE", "")
val redisHost = dotenv.get("REDIS_HOST")
val redisPort = dotenv.get("REDIS_PORT", "6379").toInt()
val storageDirPath = dotenv.get("STORAGE_DIR_PATH", "storage")
