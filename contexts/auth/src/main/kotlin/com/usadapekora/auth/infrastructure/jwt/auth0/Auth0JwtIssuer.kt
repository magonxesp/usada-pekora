package com.usadapekora.auth.infrastructure.jwt.auth0

import arrow.core.Either
import arrow.core.right
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.usadapekora.auth.domain.jwt.Jwt
import com.usadapekora.auth.domain.jwt.JwtError
import com.usadapekora.auth.domain.jwt.JwtIssuer
import com.usadapekora.auth.domain.shared.AuthorizationGrant
import com.usadapekora.auth.jwkKeyId
import com.usadapekora.shared.jwtAudience
import com.usadapekora.shared.jwtIssuer
import com.usadapekora.auth.privateKeyPath
import com.usadapekora.auth.publicKeyPath
import kotlinx.datetime.*
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

class Auth0JwtIssuer(private val clock: Clock) : JwtIssuer {

    override fun issue(authorization: AuthorizationGrant, expirationTimeInSeconds: Int): Either<JwtError, Jwt> {
        val privateKeyContent = Files.readString(Paths.get(privateKeyPath)).trimKey()
        val publicKeyContent = Files.readString(Paths.get(publicKeyPath)).trimKey()

        val factory = KeyFactory.getInstance("RSA")
        val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent))
        val privateKey = factory.generatePrivate(keySpecPKCS8) as RSAPrivateKey

        val keySpecX509 = X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent))
        val publicKey = factory.generatePublic(keySpecX509) as RSAPublicKey

        val algorithm = Algorithm.RSA256(publicKey, privateKey)
        val expiresAt = clock.now().plus(expirationTimeInSeconds.toLong(), DateTimeUnit.SECOND)

        val token = JWT.create()
            .withSubject(UUID.randomUUID().toString())
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withPayload(mapOf("userId" to authorization.user.value))
            .withExpiresAt(expiresAt.toJavaInstant())
            .withIssuedAt(clock.now().toJavaInstant())
            .withKeyId(jwkKeyId)
            .sign(algorithm)

        return Jwt.fromPrimitives(token, expiresAt).right()
    }
}
