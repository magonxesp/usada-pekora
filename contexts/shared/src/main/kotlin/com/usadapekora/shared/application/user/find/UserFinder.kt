package com.usadapekora.shared.application.user.find

import arrow.core.Either
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.domain.user.UserException
import com.usadapekora.shared.domain.user.UserRepository

class UserFinder(private val repository: UserRepository) {

    fun find(id: User.UserId): Either<UserException.NotFound, UserResponse>
        = repository.find(id).map { UserResponse.fromEntity(it) }

    fun findByProviderId(providerId: User.UserProviderId): Either<UserException.NotFound, UserResponse>
        = repository.findByProviderId(providerId).map { UserResponse.fromEntity(it) }

}
