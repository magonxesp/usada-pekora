package com.usadapekora.shared.application.user.find

import arrow.core.Either
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.domain.user.UserException
import com.usadapekora.shared.domain.user.UserRepository

class UserFinder(private val repository: UserRepository) {

    private fun mapResponse(user: User) = UserResponse(
        id = user.id.value,
        avatar = user.avatar?.value,
        name = user.name.value,
        providerId = user.providerId.value,
        provider = user.provider.value
    )

    fun find(id: User.UserId): Either<UserException.NotFound, UserResponse>
        = repository.find(id).map { mapResponse(it) }

    fun findByProviderId(providerId: User.UserProviderId): Either<UserException.NotFound, UserResponse>
        = repository.findByProviderId(providerId).map { mapResponse(it) }

}
