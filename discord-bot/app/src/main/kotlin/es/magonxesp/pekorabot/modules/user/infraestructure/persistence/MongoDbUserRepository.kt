package es.magonxesp.pekorabot.modules.user.infraestructure.persistence

import es.magonxesp.pekorabot.modules.user.domain.User
import es.magonxesp.pekorabot.modules.user.domain.UserException
import es.magonxesp.pekorabot.modules.user.domain.UserRepository
import es.magonxesp.pekorabot.mongoConnectionUrl
import es.magonxesp.pekorabot.mongoDatabase
import org.litote.kmongo.*

class MongoDbUserRepository : UserRepository {

    private val client = KMongo.createClient(mongoConnectionUrl)
    private val database = client.getDatabase(mongoDatabase)
    private val collection = database.getCollection<User>()

    override fun find(id: String): User {
        val user = collection.findOne(User::id eq id)

        if (user != null) {
            return user
        }

        throw UserException.NotFound("User with id $id not found")
    }

    override fun findByDiscordId(discordId: String): User {
        val user = collection.findOne(User::discordId eq discordId)

        if (user != null) {
            return user
        }

        throw UserException.NotFound("User with discord id $discordId not found")
    }

    override fun save(user: User) {
        if (collection.findOne(User::id eq user.id) != null) {
            collection.updateOne(user)
        } else {
            collection.insertOne(user)
        }
    }

    override fun delete(user: User) {
        collection.deleteOne(User::id eq user.id)
    }

}
