package com.usadapekora.auth.domain.oauth

import com.usadapekora.shared.domain.user.User
import jakarta.xml.bind.DatatypeConverter
import java.security.MessageDigest
import java.time.Instant
import java.util.Base64

class OAuthAuthorizationGrantCodeCreator {

    private fun md5(value: String) = DatatypeConverter.printHexBinary(
        MessageDigest.getInstance("MD5").digest(value.toByteArray())
    )

    fun fromOAuthUser(oAuthUser: OAuthUser, userId: User.UserId): OAuthAuthorizationGrant.OAuthAuthorizationGrantCode {
        val base64Encoder = Base64.getEncoder()
        val encodedUserId = md5(userId.value)
        val encodedOAuthUserId = md5(oAuthUser.id)
        val encodedIssuedAt = Instant.now().epochSecond.toString()
        val code = base64Encoder.encode("$encodedIssuedAt.$encodedUserId.$encodedOAuthUserId".toByteArray()).toString(charset = Charsets.UTF_8)

        return OAuthAuthorizationGrant.OAuthAuthorizationGrantCode(code)
    }
}
