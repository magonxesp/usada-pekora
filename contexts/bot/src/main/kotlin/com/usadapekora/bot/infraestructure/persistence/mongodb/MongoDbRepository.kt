package com.usadapekora.bot.infraestructure.persistence.mongodb

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.usadapekora.shared.domain.Entity
import com.usadapekora.bot.mongoConnectionUrl
import com.usadapekora.bot.mongoDatabase
import org.litote.kmongo.*
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

    fun save(entity: E) {
        writeQuery<Any>(collection) { collection ->
            collection.updateOne(documentIdProp eq entity.id(), documentCompanion.fromEntity(entity))
                .takeUnless { updateResult -> updateResult.modifiedCount > 0L }
                ?.apply { collection.insertOne(documentCompanion.fromEntity(entity)) }
        }
    }

    fun delete(entity: E) {
        writeQuery<Any>(collection) { collection ->
            collection.deleteOne(documentIdProp eq entity.id())
        }
    }
}
