package com.usadapekora.shared.domain.user

import com.usadapekora.shared.domain.Entity
import com.usadapekora.shared.domain.valueobject.UuidValueObject
import kotlinx.datetime.Instant

data class UserSession(
    val id: UserSessionId,
    val userId: User.UserId,
    val state: UserSessionState,
    val expiresAt: UserSessionExpiresAt,
    val lastActiveAt: UserSessionLastActiveAt,
    val device: UserSessionDevice,
) : Entity() {
    data class UserSessionId(override val value: String) : UuidValueObject(value = value)
    enum class UserSessionState {
        OPEN, CLOSE
    }
    data class UserSessionExpiresAt(val value: Instant)
    data class UserSessionLastActiveAt(val value: Instant)
    data class UserSessionDevice(val value: String)

    companion object {
        fun fromPrimitives(
            id: String,
            userId: String,
            state: String,
            expiresAt: Instant,
            lastActiveAt: Instant,
            device: String,
        ) = UserSession(
            id = UserSessionId(id),
            userId = User.UserId(userId),
            state = UserSessionState.valueOf(state.uppercase()),
            expiresAt = UserSessionExpiresAt(expiresAt),
            lastActiveAt = UserSessionLastActiveAt(lastActiveAt),
            device = UserSessionDevice(device)
        )
    }

    override fun id() = id.value
}
