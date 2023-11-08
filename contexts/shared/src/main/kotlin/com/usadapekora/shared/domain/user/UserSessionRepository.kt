package com.usadapekora.shared.domain.user

import arrow.core.Either

interface UserSessionRepository {
    fun find(id: UserSession.UserSessionId): Either<UserSessionException.NotFound, UserSession>
    fun save(entity: UserSession): Either<UserSessionException.SaveError, Unit>
}
