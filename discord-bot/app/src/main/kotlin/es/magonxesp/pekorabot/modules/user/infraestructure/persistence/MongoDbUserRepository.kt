package es.magonxesp.pekorabot.modules.user.infraestructure.persistence

import es.magonxesp.pekorabot.modules.shared.infraestructure.persistence.MongoDbRepository
import es.magonxesp.pekorabot.modules.user.domain.User
import es.magonxesp.pekorabot.modules.user.domain.UserException
import es.magonxesp.pekorabot.modules.user.domain.UserRepository
import org.litote.kmongo.*

class MongoDbUserRepository : MongoDbRepository(), UserRepository {

    override fun find(id: String): User {
        val user = oneQuery<User>("user") { collection ->
            collection.findOne(User::id eq id)
        }

        if (user != null) {
            return user
        }

        throw UserException.NotFound("User with id $id not found")
    }

    override fun findByDiscordId(discordId: String): User {
        val user = oneQuery<User>("user") { collection ->
            collection.findOne(User::discordId eq discordId)
        }

        if (user != null) {
            return user
        }

        throw UserException.NotFound("User with discord id $discordId not found")
    }

    override fun save(user: User) {
        writeQuery<User>("user") { collection ->
            if (collection.findOne(User::id eq user.id) != null) {
                collection.updateOne(user)
            } else {
                collection.insertOne(user)
            }
        }
    }

    override fun delete(user: User) {
        writeQuery<User>("user") { collection ->
            collection.deleteOne(User::id eq user.id)
        }
    }

}
