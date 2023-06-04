package com.usadapekora.auth.infrastructure.jwt.auth0

import arrow.core.Either
import arrow.core.right
import com.nimbusds.jose.Algorithm
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import com.usadapekora.auth.domain.jwt.JwkError
import com.usadapekora.auth.domain.jwt.JwkIssuer
import com.usadapekora.auth.jwkKeyId
import com.usadapekora.auth.privateKeyPath
import com.usadapekora.auth.publicKeyPath
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*


class Auth0JwkIssuer(private val clock: Clock) : JwkIssuer {

    override fun issue(): Either<JwkError, String> = Either.catch {
        val privateKeyContent = Files.readString(Paths.get(privateKeyPath)).trimKey()
        val publicKeyContent = Files.readString(Paths.get(publicKeyPath)).trimKey()

        val factory = KeyFactory.getInstance("RSA")
        val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent))
        val privateKey = factory.generatePrivate(keySpecPKCS8) as RSAPrivateKey

        val keySpecX509 = X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent))
        val publicKey = factory.generatePublic(keySpecX509) as RSAPublicKey

        val jwk: JWK = RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyUse(KeyUse.SIGNATURE)
            .keyID(jwkKeyId)
            .algorithm(Algorithm.parse("RSA256"))
            .issueTime(Date.from(clock.now().toJavaInstant()))
            .build()

        jwk.toJSONString()
    }.mapLeft {
        JwkError(message = it.message)
    }

}
