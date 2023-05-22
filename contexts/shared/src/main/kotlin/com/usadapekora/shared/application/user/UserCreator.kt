package com.usadapekora.shared.application.user

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.shared.domain.exception.InvalidUuidException
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.domain.user.UserException
import com.usadapekora.shared.domain.user.UserRepository

class UserCreator(private val repository: UserRepository) {

    fun create(id: String, name: String, avatar: String?, discordId: String): Either<UserException, Unit> {
        repository.find(User.UserId(id)).getOrNull()?.let {
            return UserException.AlreadyExists("User with id $id already exists").left()
        }

        val user = Either.catch {
            User.fromPrimitives(
                id = id,
                name = name,
                avatar = avatar,
                discordId = discordId
            )
        }.let {
            if (it.leftOrNull() != null) return when(it.leftOrNull()!!) {
                is InvalidUuidException -> UserException.FailedToCreate("The id should be an uuid")
                else -> UserException.FailedToCreate("An error ocurred creating the user")
            }.left()

            it.getOrNull()!!
        }

        return repository.save(user).right()
    }

}
