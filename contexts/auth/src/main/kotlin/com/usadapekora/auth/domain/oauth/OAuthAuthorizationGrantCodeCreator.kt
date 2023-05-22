package com.usadapekora.auth.domain.oauth

import com.usadapekora.shared.domain.user.User
import java.security.MessageDigest
import java.time.Instant
import java.util.Base64

class OAuthAuthorizationGrantCodeCreator {
    fun fromOAuthUser(oAuthUser: OAuthUser, userId: User.UserId): OAuthAuthorizationGrant.OAuthAuthorizationGrantCode {
        val base64Encoder = Base64.getEncoder()
        val encodedUserId = base64Encoder.encode(userId.value.toByteArray(charset = Charsets.UTF_8))
        val encodedOAuthUserId = base64Encoder.encode(oAuthUser.id.toByteArray(charset = Charsets.UTF_8))
        val encodedIssuedAt = base64Encoder.encode(Instant.now().epochSecond.toString().toByteArray(charset = Charsets.UTF_8))
        val code = MessageDigest.getInstance("MD5")
            .digest("$encodedIssuedAt.$encodedUserId.$encodedOAuthUserId".toByteArray())
            .toString(charset = Charsets.UTF_8)

        return OAuthAuthorizationGrant.OAuthAuthorizationGrantCode(code)
    }
}
