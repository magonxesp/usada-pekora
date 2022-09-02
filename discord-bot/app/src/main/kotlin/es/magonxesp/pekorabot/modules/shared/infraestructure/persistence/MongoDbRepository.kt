package es.magonxesp.pekorabot.modules.shared.infraestructure.persistence

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import es.magonxesp.pekorabot.mongoConnectionUrl
import es.magonxesp.pekorabot.mongoDatabase
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollectionOfName
import kotlin.concurrent.thread

abstract class MongoDbRepository {
    
    companion object {
        private var client: MongoClient? = null

        fun connect(): MongoDatabase {
            if (client == null) {
                client = KMongo.createClient(mongoConnectionUrl)

                Runtime.getRuntime().addShutdownHook(thread(start = false) {
                    client!!.close()
                    client = null
                })
            }

            return client!!.getDatabase(mongoDatabase)
        }
    }

    inline fun <reified T: Any> oneQuery(name: String, collectionCallback: (collection: MongoCollection<T>) -> T?): T? {
        val database = connect()
        val collection = database.getCollectionOfName<T>(name)

        return collectionCallback(collection)
    }

    inline fun <reified T: Any> collectionQuery(name: String, collectionCallback: (collection: MongoCollection<T>) -> FindIterable<T>): FindIterable<T> {
        val database = connect()
        val collection = database.getCollectionOfName<T>(name)

        return collectionCallback(collection)
    }

    inline fun <reified T: Any> writeQuery(name: String, collectionCallback: (collection: MongoCollection<T>) -> Unit) {
        val database = connect()
        val collection = database.getCollectionOfName<T>(name)

        collectionCallback(collection)
    }

}
