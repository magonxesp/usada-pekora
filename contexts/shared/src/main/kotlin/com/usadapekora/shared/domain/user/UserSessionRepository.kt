package com.usadapekora.shared.domain.user

import arrow.core.Either

interface UserSessionRepository {
    fun find(id: UserSession.UserSessionId): Either<UserSessionError.NotFound, UserSession>
    fun save(entity: UserSession): Either<UserSessionError.SaveError, Unit>
}
