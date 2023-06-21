package com.usadapekora.auth.infrastructure.shared.persistence.redis

import com.usadapekora.auth.domain.shared.AuthorizationGrant
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class AuthorizationGrantJson(
    val code: String,
    val user: String,
    val expiresAt: Int,
    val issuedAt: Long
) {
    companion object {
        fun fromEntity(entity: AuthorizationGrant) = AuthorizationGrantJson(
            code = entity.code.value,
            user = entity.user.value,
            expiresAt = entity.expiresAt.seconds,
            issuedAt = entity.issuedAt.value.toEpochMilliseconds()
        )

        fun fromJsonString(json: String) = Json.decodeFromString<AuthorizationGrantJson>(json)
    }

    fun toJsonString() = Json.encodeToString(this)

    fun toEntity() = AuthorizationGrant.fromPrimitives(
        code = code,
        user = user,
        expiresAt = expiresAt,
        issuedAt = Instant.fromEpochMilliseconds(issuedAt)
    )
}
