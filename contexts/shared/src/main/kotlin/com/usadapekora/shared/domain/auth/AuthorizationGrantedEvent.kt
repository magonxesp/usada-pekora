package com.usadapekora.shared.domain.auth

import com.usadapekora.shared.domain.bus.event.Event
import com.usadapekora.shared.domain.bus.event.EventName
import kotlinx.datetime.Clock
import java.util.UUID

@EventName("auth.authorization_granted")
data class AuthorizationGrantedEvent(
    override val id: String = UUID.randomUUID().toString(),
    override val occurredOn: String = Clock.System.now().toString(),
    val userId: String
) : Event()
