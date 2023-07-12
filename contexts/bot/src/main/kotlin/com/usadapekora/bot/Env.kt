package com.usadapekora.bot

import com.usadapekora.shared.env

val backendBaseUrl = env("BOT_BACKEND_BASE_URL", "http://localhost:8080").removeSuffix("/")
