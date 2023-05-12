package com.usadapekora.bot.application.user

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.shared.exception.InvalidUuidException
import com.usadapekora.bot.domain.user.User
import com.usadapekora.bot.domain.user.UserException
import com.usadapekora.bot.domain.user.UserRepository

class UserCreator(private val repository: UserRepository) {

    fun create(id: String, discordId: String): Either<UserException, Unit> {
        repository.find(User.UserId(id)).getOrNull()?.let {
            return UserException.AlreadyExists("User with id $id already exists").left()
        }

        val user = Either.catch {
            User.fromPrimitives(
                id = id,
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
