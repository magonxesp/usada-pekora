package com.usadapekora.shared.application.user.create

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.shared.domain.exception.InvalidUuidException
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.domain.user.UserException
import com.usadapekora.shared.domain.user.UserRepository

class UserCreator(private val repository: UserRepository) {

    fun create(request: UserCreateRequest): Either<UserException, Unit> {
        repository.find(User.UserId(request.id))
            .onRight { return UserException.AlreadyExists("User with id ${request.id} already exists").left() }

        val user = Either.catch {
            User.fromPrimitives(
                id = request.id,
                name = request.name,
                avatar = request.avatar,
                providerId = request.providerId,
                provider = request.provider
            )
        }.mapLeft {
            when(it) {
                is InvalidUuidException -> UserException.FailedToCreate("The id should be an uuid")
                else -> UserException.FailedToCreate("An error ocurred creating the user")
            }
        }.onLeft { return it.left() }.getOrNull()!!

        return repository.save(user).right()
    }

}
