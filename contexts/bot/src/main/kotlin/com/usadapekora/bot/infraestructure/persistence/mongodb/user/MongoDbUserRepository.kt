package com.usadapekora.bot.infraestructure.persistence.mongodb.user

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbRepository
import com.usadapekora.bot.domain.user.User
import com.usadapekora.bot.domain.user.UserException
import com.usadapekora.bot.domain.user.UserRepository
import org.litote.kmongo.*

class MongoDbUserRepository : MongoDbRepository<User, UserDocument>(
    collection = "user",
    documentIdProp = UserDocument::id,
    documentCompanion = UserDocument.Companion
), UserRepository {

    override fun find(id: User.UserId): Either<UserException.NotFound, User> {
        val user = oneQuery<UserDocument>("user") { collection ->
            collection.findOne(UserDocument::id eq id.value)
        }

        if (user != null) {
            return user.toEntity().right()
        }

        return UserException.NotFound("User with id $id not found").left()
    }

    override fun findByDiscordId(discordId: User.DiscordUserId): Either<UserException.NotFound, User> {
        val user = oneQuery<UserDocument>("user") { collection ->
            collection.findOne(UserDocument::discordId eq discordId.value)
        }

        if (user != null) {
            return user.toEntity().right()
        }

        return UserException.NotFound("User with discord id $discordId not found").left()
    }

}
