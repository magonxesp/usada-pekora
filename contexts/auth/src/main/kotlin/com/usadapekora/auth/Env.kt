package com.usadapekora.auth

import com.usadapekora.shared.env

val oAuthProviderRedirectUrl = env("OAUTH_PROVIDER_REDIRECT_URL", "http://localhost:3000/%provider%/callback")
val jwkKeyId = env("AUTH_JWK_KEY_ID", "usadapekora")
val publicKeyPath = env("AUTH_SSL_PUBLIC_KEY_PATH", "public.pem")
val privateKeyPath = env("AUTH_SSL_PRIVATE_KEY_PATH", "private.pem")
