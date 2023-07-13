package com.usadapekora.shared.infrastructure.user.persistence.mongodb

import com.usadapekora.shared.domain.user.UserSession
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDomainEntityDocument
import kotlinx.datetime.Instant

class UserSessionDocument(
    var id: String = "",
    var userId: String = "",
    var state: String = "",
    var expiresAt: String = "",
    var lastActiveAt: String = "",
    var device: String = "",
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<UserSession, UserSessionDocument>({ UserSessionDocument() }) {
        override fun fromEntity(entity: UserSession, document: UserSessionDocument) = document.apply {
            id = entity.id.value
            userId = entity.userId.value
            state = entity.state.name.lowercase()
            expiresAt = entity.expiresAt.value.toString()
            lastActiveAt = entity.lastActiveAt.value.toString()
            device = entity.device.value
        }
    }

    fun toEntity() = UserSession.fromPrimitives(
        id = id,
        userId = userId,
        state = state,
        expiresAt = Instant.parse(expiresAt),
        lastActiveAt = Instant.parse(lastActiveAt),
        device = device
    )
}
