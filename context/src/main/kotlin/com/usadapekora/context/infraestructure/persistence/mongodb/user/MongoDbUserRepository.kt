package com.usadapekora.context.infraestructure.persistence.mongodb.user

import com.usadapekora.context.infraestructure.persistence.mongodb.MongoDbRepository
import com.usadapekora.context.domain.user.User
import com.usadapekora.context.domain.user.UserException
import com.usadapekora.context.domain.user.UserRepository
import org.litote.kmongo.*

class MongoDbUserRepository : MongoDbRepository<User, UserDocument>(
    collection = "user",
    documentIdProp = UserDocument::id,
    documentCompanion = UserDocument.Companion
), UserRepository {

    override fun find(id: String): User {
        val user = oneQuery<UserDocument>("user") { collection ->
            collection.findOne(UserDocument::id eq id)
        }

        if (user != null) {
            return user.toEntity()
        }

        throw UserException.NotFound("User with id $id not found")
    }

    override fun findByDiscordId(discordId: String): User {
        val user = oneQuery<UserDocument>("user") { collection ->
            collection.findOne(User::discordId eq discordId)
        }

        if (user != null) {
            return user.toEntity()
        }

        throw UserException.NotFound("User with discord id $discordId not found")
    }

}
