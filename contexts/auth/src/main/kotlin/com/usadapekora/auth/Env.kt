package com.usadapekora.auth

import com.usadapekora.shared.env

val discordBotToken = env("DISCORD_BOT_TOKEN")
val discordClientId = env("DISCORD_CLIENT_ID")
val discordClientSecret = env("DISCORD_CLIENT_SECRET")
val mongoConnectionUrl = env("MONGODB_URL", "mongodb://example:example@localhost:27017")
val mongoDatabase = env("MONGODB_DATABASE", "usada_pekora")
val redisHost = env("REDIS_HOST", "localhost")
val redisPort = env("REDIS_PORT", "6379").toInt()
val oAuthProviderRedirectUrl = env("OAUTH_PROVIDER_REDIRECT_URL", "http://localhost:3000/%provider%/callback") // new
val jwtIssuer = env("AUTH_JWT_ISSUER", "usada-pekora")
val jwtAudience = env("AUTH_JWT_ISSUER", "usada-pekora")
val publicKeyPath = env("AUTH_SSL_PUBLIC_KEY_PATH", "public.pem")
val privateKeyPath = env("AUTH_SSL_PRIVATE_KEY_PATH", "private.pem")
