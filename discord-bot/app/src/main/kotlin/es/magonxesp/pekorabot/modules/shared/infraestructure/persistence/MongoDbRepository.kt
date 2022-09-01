package es.magonxesp.pekorabot.modules.shared.infraestructure.persistence

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import es.magonxesp.pekorabot.mongoConnectionUrl
import es.magonxesp.pekorabot.mongoDatabase
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollectionOfName

abstract class MongoDbRepository {

    lateinit var client: MongoClient

    fun connect(): MongoDatabase {
        client = KMongo.createClient(mongoConnectionUrl)
        return client.getDatabase(mongoDatabase)
    }

    inline fun <reified T: Any> oneQuery(name: String, collectionCallback: (collection: MongoCollection<T>) -> T?): T? {
        val database = connect()
        val collection = database.getCollectionOfName<T>(name)

        val result = collectionCallback(collection)

        client.close()
        return result
    }

    inline fun <reified T: Any> collectionQuery(name: String, collectionCallback: (collection: MongoCollection<T>) -> FindIterable<T>): FindIterable<T> {
        val database = connect()
        val collection = database.getCollectionOfName<T>(name)

        val result = collectionCallback(collection)

        client.close()
        return result
    }

    inline fun <reified T: Any> writeQuery(name: String, collectionCallback: (collection: MongoCollection<T>) -> Unit) {
        val database = connect()
        val collection = database.getCollectionOfName<T>(name)

        collectionCallback(collection)

        client.close()
    }

}
