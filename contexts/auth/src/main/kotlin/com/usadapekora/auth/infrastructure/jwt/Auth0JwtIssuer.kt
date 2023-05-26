package com.usadapekora.auth.infrastructure.jwt

import arrow.core.Either
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.usadapekora.auth.domain.jwt.Jwt
import com.usadapekora.auth.domain.jwt.JwtError
import com.usadapekora.auth.domain.jwt.JwtIssuer
import com.usadapekora.auth.domain.shared.AuthorizationGrant
import java.time.Instant
import java.util.*

class Auth0JwtIssuer : JwtIssuer {
    override fun issue(code: AuthorizationGrant, expirationTimeInSeconds: Int): Either<JwtError, Jwt> {
//        val privateKeyContent = Files.readString(Paths.get("ssl", "private.pem")).trimKey()
//        val publicKeyContent = Files.readString(Paths.get("ssl", "static.pem")).trimKey()
//
//        val factory = KeyFactory.getInstance("RSA")
//        val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent))
//        val privateKey = factory.generatePrivate(keySpecPKCS8) as RSAPrivateKey
//
//        val keySpecX509 = X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent))
//        val publicKey = factory.generatePublic(keySpecX509) as RSAPublicKey

        val algorithm = Algorithm.HMAC256("paco")
        val token = JWT.create()
            .withSubject("pepe")
            .withAudience("com.usada-pekora.bot")
            .withIssuer("com.usada-pekora.auth")
            .withPayload(mapOf("uuid" to UUID.randomUUID().toString()))
            .withExpiresAt(Instant.now().plusSeconds(expirationTimeInSeconds.toLong()))
            .sign(algorithm)

        // TODO: generate subject by the authorization code
        // TODO: get audience and issuer by environment variable
    }
}
