package es.magonxesp.pekorabot

val backendBaseUrl = System.getenv("BACKEND_BASE_URL").removeSuffix("/")
val backendToken = System.getenv("BACKEND_TOKEN")
val discordBotToken = System.getenv("DISCORD_BOT_TOKEN")
val httpBaseUrl = (System.getenv("HTTP_BASE_URL") ?: "http://localhost:8080").removeSuffix("/")
val youtubeChannelId = System.getenv("YOUTUBE_CHANNEL_ID") ?: "UC1DCedRgGHBdm81E1llLhOQ"
