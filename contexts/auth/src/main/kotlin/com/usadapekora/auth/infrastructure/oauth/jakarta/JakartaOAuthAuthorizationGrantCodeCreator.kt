package com.usadapekora.auth.infrastructure.oauth.jakarta

import com.usadapekora.auth.domain.shared.AuthorizationGrant
import com.usadapekora.auth.domain.oauth.OAuthAuthorizationGrantCodeCreator
import com.usadapekora.auth.domain.oauth.OAuthUser
import com.usadapekora.shared.domain.user.User
import jakarta.xml.bind.DatatypeConverter
import java.security.MessageDigest
import kotlinx.datetime.Clock
import io.ktor.util.encodeBase64

class JakartaOAuthAuthorizationGrantCodeCreator : OAuthAuthorizationGrantCodeCreator {

    private fun md5(value: String) = DatatypeConverter.printHexBinary(
        MessageDigest.getInstance("MD5").digest(value.toByteArray())
    )

    override fun fromOAuthUser(oAuthUser: OAuthUser, userId: User.UserId): AuthorizationGrant.AuthorizationGrantCode {
        val encodedUserId = md5(userId.value)
        val encodedOAuthUserId = md5(oAuthUser.id)
        val encodedIssuedAt = Clock.System.now().epochSeconds.toString()
        val code = "$encodedIssuedAt.$encodedUserId.$encodedOAuthUserId".encodeBase64()

        return AuthorizationGrant.AuthorizationGrantCode(code)
    }
}
