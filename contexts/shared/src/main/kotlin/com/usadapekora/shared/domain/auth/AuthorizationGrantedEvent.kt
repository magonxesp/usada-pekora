package com.usadapekora.shared.domain.auth

import com.usadapekora.shared.domain.bus.Event
import kotlinx.datetime.Clock
import java.util.UUID

data class AuthorizationGrantedEvent(
    override val id: String = UUID.randomUUID().toString(),
    override val occurredOn: String = Clock.System.now().toString(),
    val userId: String
) : Event() {
    override val name: String = "auth.authorization_granted"
}
