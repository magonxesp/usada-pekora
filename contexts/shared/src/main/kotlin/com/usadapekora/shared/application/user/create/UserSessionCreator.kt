package com.usadapekora.shared.application.user.create

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.shared.domain.user.UserSession
import com.usadapekora.shared.domain.user.UserSessionException
import com.usadapekora.shared.domain.user.UserSessionRepository

class UserSessionCreator(private val repository: UserSessionRepository) {
    fun create(request: UserSessionCreateRequest): Either<UserSessionException, Unit> {
        val session = UserSession.fromPrimitives(
            id = request.id,
            userId = request.userId,
            state = request.state,
            expiresAt = request.expiresAt,
            lastActiveAt = request.lastActiveAt,
            device = request.device
        )

        repository.find(session.id)
            .onRight { return UserSessionException.AlreadyExists("The user session with id ${request.id} for user id ${request.userId} already exists").left() }

        return repository.save(session)
            .onLeft { return it.left() }
            .getOrNull()!!.right()
    }
}
