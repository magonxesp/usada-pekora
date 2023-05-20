package com.usadapekora.bot

import com.usadapekora.shared.env

val discordBotToken = env("DISCORD_BOT_TOKEN")
val backendBaseUrl = env("BACKEND_BASE_URL", "http://localhost:8080").removeSuffix("/")
val youtubeChannelId = env("YOUTUBE_CHANNEL_ID", "UC1DCedRgGHBdm81E1llLhOQ")
val storageDirPath = env("STORAGE_DIR_PATH", "storage")

// test environment variables
val testDiscordTextChannelId = env("TEST_DISCORD_CHANNEL_ID")
val testDiscordGuildId = env("TEST_DISCORD_GUILD_ID")
