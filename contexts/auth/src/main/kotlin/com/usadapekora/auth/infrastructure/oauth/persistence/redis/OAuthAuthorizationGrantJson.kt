package com.usadapekora.auth.infrastructure.oauth.persistence.redis

import com.usadapekora.auth.domain.oauth.OAuthAuthorizationGrant
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

@Serializable
data class OAuthAuthorizationGrantJson(
    val code: String,
    val user: String,
    val expiresAt: Int,
    val issuedAt: Long
) {
    companion object {
        fun fromEntity(entity: OAuthAuthorizationGrant) = OAuthAuthorizationGrantJson(
            code = entity.code.value,
            user = entity.user.value,
            expiresAt = entity.expiresAt.seconds,
            issuedAt = entity.issuedAt.value.toEpochMilliseconds()
        )

        fun fromJsonString(json: String) = Json.decodeFromString<OAuthAuthorizationGrantJson>(json)
    }

    fun toJsonString() = Json.encodeToString(this)

    fun toEntity() = OAuthAuthorizationGrant.fromPrimitives(
        code = code,
        user = user,
        expiresAt = expiresAt,
        issuedAt = Instant.fromEpochMilliseconds(issuedAt)
    )
}
