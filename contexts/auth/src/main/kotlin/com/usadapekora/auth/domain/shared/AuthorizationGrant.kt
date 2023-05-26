package com.usadapekora.auth.domain.shared

import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.domain.common.valueobject.DateTimeValueObject
import kotlinx.datetime.Instant

data class AuthorizationGrant(
    val code: AuthorizationGrantCode,
    val user: User.UserId,
    val expiresAt: AuthorizationGrantExpiresAt,
    val issuedAt: AuthorizationGrantIssuedAt
) {
    data class AuthorizationGrantCode(val value: String)
    data class AuthorizationGrantExpiresAt(val seconds: Int)
    class AuthorizationGrantIssuedAt(override val value: Instant) : DateTimeValueObject(value = value)

    companion object {
        fun fromPrimitives(
            code: String,
            user: String,
            expiresAt: Int,
            issuedAt: Instant
        ) = AuthorizationGrant(
            code = AuthorizationGrantCode(code),
            user = User.UserId(user),
            expiresAt = AuthorizationGrantExpiresAt(expiresAt),
            issuedAt = AuthorizationGrantIssuedAt(issuedAt)
        )
    }
}
