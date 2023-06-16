package com.usadapekora.shared.domain.auth

import com.usadapekora.shared.domain.bus.Event

data class AuthorizationGrantedEvent(
    override val id: String,
    override val occurredOn: String,
    val userId: String
) : Event(
    id = id,
    occurredOn = occurredOn
) {
    override val name: String = "auth.authorization_granted"
}
