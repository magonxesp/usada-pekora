package com.usadapekora.shared.domain.auth

import arrow.core.Either
import com.usadapekora.shared.domain.user.User

interface OAuthUserRepository {
    fun find(userId: User.UserId): Either<OAuthUserException.NotFound, OAuthUser>
    fun save(entity: OAuthUser): Either<OAuthUserException.SaveError, Unit>
}
