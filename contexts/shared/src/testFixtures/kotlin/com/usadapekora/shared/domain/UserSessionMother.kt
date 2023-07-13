package com.usadapekora.shared.domain

import com.usadapekora.shared.domain.user.UserSession
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.*

object UserSessionMother : ObjectMother<UserSession> {

    fun create(
        id: String? = null,
        userId: String? = null,
        state: String? = null,
        expiresAt: Instant? = null,
        lastActiveAt: Instant? = null,
        device: String? = null,
    ) = UserSession.fromPrimitives(
        id = id ?: UUID.randomUUID().toString(),
        userId = userId ?: UUID.randomUUID().toString(),
        state = state ?: Random.instance().random.nextEnum(UserSession.UserSessionState::class.java).name,
        expiresAt = expiresAt ?: Clock.System.now(),
        lastActiveAt = lastActiveAt ?: Clock.System.now(),
        device = device ?: Random.instance().internet.userAgent(""),
    )

    override fun random(): UserSession = create()
}
