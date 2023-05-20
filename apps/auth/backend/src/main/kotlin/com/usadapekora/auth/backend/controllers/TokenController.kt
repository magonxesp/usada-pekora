package com.usadapekora.auth.backend.controllers

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAPrivateKeySpec
import java.security.spec.X509EncodedKeySpec
import java.time.Instant
import java.time.temporal.TemporalAmount
import java.time.temporal.TemporalUnit
import java.util.*


@RestController
class TokenController {

    @GetMapping("/auth/token")
    suspend fun token(@RequestParam("code") code: Optional<String>): AccessTokenResponse {
        if (code.isEmpty) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing code parameter")
        }

//        val privateKeyContent = Files.readString(Paths.get("ssl", "private.pem")).trimKey()
//        val publicKeyContent = Files.readString(Paths.get("ssl", "public.pem")).trimKey()
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
            .withExpiresAt(Instant.now().plusSeconds(7 * 24 * 60 * 60))
            .sign(algorithm)

        return AccessTokenResponse(
            accessToken = token,
            expires = 1
        )
    }

    private fun String.trimKey(): String
        = split("\n")
            .filter { !Regex("^-+[A-Z ]+-+\$").matches(it) }
            .joinToString("")

}
