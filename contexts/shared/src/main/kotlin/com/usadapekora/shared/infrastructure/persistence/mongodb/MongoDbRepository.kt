package com.usadapekora.shared.infrastructure.persistence.mongodb

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.usadapekora.shared.domain.Entity
import com.usadapekora.shared.mongoConnectionUrl
import com.usadapekora.shared.mongoDatabase
import org.litote.kmongo.KMongo
import org.litote.kmongo.eq
import org.litote.kmongo.getCollectionOfName
import org.litote.kmongo.updateOne
import kotlin.concurrent.thread
import kotlin.reflect.KProperty

abstract class MongoDbRepository<E: Entity, D : MongoDbDocument>(
    val collection: String,
    val documentIdProp: KProperty<*>,
    val documentCompanion: MongoDbDomainEntityDocument<E, D>
) {

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

    inline fun <reified T: Any> collectionQuery(collectionName: String, collectionCallback: (collection: MongoCollection<T>) -> FindIterable<T>): FindIterable<T> {
        val database = connect()
        val collection = database.getCollectionOfName<T>(collectionName)

        return collectionCallback(collection)
    }

    inline fun <reified T: Any> writeQuery(collectionName: String, collectionCallback: (collection: MongoCollection<T>) -> Unit) {
        val database = connect()
        val collection = database.getCollectionOfName<T>(collectionName)

        collectionCallback(collection)
    }

    open fun performSave(entity: E) {
        writeQuery<Any>(collection) { collection ->
            collection.updateOne(documentIdProp eq entity.id(), documentCompanion.fromEntity(entity))
                .takeUnless { updateResult -> updateResult.modifiedCount > 0L }
                ?.apply { collection.insertOne(documentCompanion.fromEntity(entity)) }
        }
    }

    open fun performDelete(entity: E) {
        writeQuery<Any>(collection) { collection ->
            collection.deleteOne(documentIdProp eq entity.id())
        }
    }
}
