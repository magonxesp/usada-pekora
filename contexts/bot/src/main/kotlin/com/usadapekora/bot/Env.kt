package com.usadapekora.bot

import com.usadapekora.shared.env

val discordBotToken = env("DISCORD_BOT_TOKEN")
val backendBaseUrl = env("BACKEND_BASE_URL", "http://localhost:8080").removeSuffix("/")
val youtubeChannelId = env("YOUTUBE_CHANNEL_ID", "UC1DCedRgGHBdm81E1llLhOQ")
val mongoConnectionUrl = env("MONGODB_URL", "mongodb://example:example@localhost:27017")
val mongoDatabase = env("MONGODB_DATABASE", "usada_pekora")
val redisHost = env("REDIS_HOST", "localhost")
val redisPort = env("REDIS_PORT", "6379").toInt()
val storageDirPath = env("STORAGE_DIR_PATH", "storage")

// test environment variables
val testDiscordTextChannelId = env("TEST_DISCORD_CHANNEL_ID")
val testDiscordGuildId = env("TEST_DISCORD_GUILD_ID")
