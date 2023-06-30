package com.usadapekora.shared.infrastructure.user.peristence.mongodb

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.shared.domain.user.User
import com.usadapekora.shared.domain.user.UserException
import com.usadapekora.shared.domain.user.UserRepository
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbRepository
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class MongoDbUserRepository : MongoDbRepository<User>(
    collection = "user",
    documentIdProp = UserDocument::id,
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

    override fun save(entity: User) {
        performSave<UserDocument>(entity, UserDocument.Companion)
    }

    override fun delete(entity: User) {
        performDelete(entity)
    }
}
