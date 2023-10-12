package com.usadapekora.shared.domain.auth

import com.usadapekora.shared.domain.bus.event.DomainEvent
import com.usadapekora.shared.domain.bus.event.DomainEventFactory
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.io.Serializable
import java.util.*

data class AuthorizationGrantedEvent(
    override val id: String = UUID.randomUUID().toString(),
    override val occurredOn: Instant = Clock.System.now(),
    val userId: String
) : DomainEvent(id, occurredOn) {
    override val name: String = "auth.authorization_granted"

    override fun attributes(): Map<String, Serializable> = mapOf(
        "userId" to userId
    )

    companion object : DomainEventFactory<AuthorizationGrantedEvent> {
        override fun fromPrimitives(
            id: String,
            occurredOn: Instant,
            name: String,
            attributes: Map<String, Serializable>
        ): AuthorizationGrantedEvent = AuthorizationGrantedEvent(
            id = id,
            occurredOn = occurredOn,
            userId = attributes["userId"]!! as String
        )
    }
}
