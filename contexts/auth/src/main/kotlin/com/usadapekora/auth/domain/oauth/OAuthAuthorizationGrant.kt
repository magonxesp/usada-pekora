package com.usadapekora.auth.domain.oauth

import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.domain.common.valueobject.DateTimeValueObject
import kotlinx.datetime.Instant

data class OAuthAuthorizationGrant(
    val code: OAuthAuthorizationGrantCode,
    val user: User.UserId,
    val expiresAt: OAuthAuthorizationGrantExpiresAt,
    val issuedAt: OAuthAuthorizationGrantIssuedAt
) {
    data class OAuthAuthorizationGrantCode(val value: String)
    data class OAuthAuthorizationGrantExpiresAt(val seconds: Int)
    class OAuthAuthorizationGrantIssuedAt(override val value: Instant) : DateTimeValueObject(value = value)

    companion object {
        fun fromPrimitives(
            code: String,
            user: String,
            expiresAt: Int,
            issuedAt: Instant
        ) = OAuthAuthorizationGrant(
            code = OAuthAuthorizationGrantCode(code),
            user = User.UserId(user),
            expiresAt = OAuthAuthorizationGrantExpiresAt(expiresAt),
            issuedAt = OAuthAuthorizationGrantIssuedAt(issuedAt)
        )
    }
}