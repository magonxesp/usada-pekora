package es.magonxesp.pekorabot

import io.github.cdimascio.dotenv.dotenv

private val dotenv = dotenv {
    filename = ".env.local"
    ignoreIfMissing = true
}

val backendBaseUrl = dotenv.get("BACKEND_BASE_URL", "").removeSuffix("/")
val backendToken = dotenv.get("BACKEND_TOKEN", "")
val discordBotToken = dotenv.get("DISCORD_BOT_TOKEN", "")
val httpBaseUrl = dotenv.get("HTTP_BASE_URL", "http://localhost:8080").removeSuffix("/")
val youtubeChannelId = dotenv.get("YOUTUBE_CHANNEL_ID", "UC1DCedRgGHBdm81E1llLhOQ")
val mongoConnectionUrl = dotenv.get("MONGODB_URL", "")
val mongoDatabase = dotenv.get("MONGODB_DATABASE", "")
val redisHost = dotenv.get("REDIS_HOST")
val redisPort = dotenv.get("REDIS_PORT", "6379").toInt()
