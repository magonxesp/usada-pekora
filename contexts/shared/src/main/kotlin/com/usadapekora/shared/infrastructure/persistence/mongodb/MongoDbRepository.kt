package com.usadapekora.shared.infrastructure.persistence.mongodb

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoIterable
import com.usadapekora.shared.domain.Entity
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollectionOfName
import org.litote.kmongo.updateOne
import kotlin.reflect.KProperty

abstract class MongoDbRepository<E: Entity>(
    val collection: String,
    val documentIdProp: KProperty<*>
) {
    inline fun <reified T: MongoDbDocument> oneQuery(name: String, collectionCallback: (collection: MongoCollection<T>) -> T?): T? {
        val database = MongoDbClientFactory.getDatabase()
        val collection = database.getCollectionOfName<T>(name)

        return collectionCallback(collection)
    }

    inline fun <reified T: MongoDbDocument> collectionQuery(collectionName: String, collectionCallback: (collection: MongoCollection<T>) -> MongoIterable<T>): MongoIterable<T> {
        val database = MongoDbClientFactory.getDatabase()
        val collection = database.getCollectionOfName<T>(collectionName)

        return collectionCallback(collection)
    }

    inline fun <reified T: MongoDbDocument> writeQuery(collectionName: String, collectionCallback: (collection: MongoCollection<T>) -> Unit) {
        val database = MongoDbClientFactory.getDatabase()
        val collection = database.getCollectionOfName<T>(collectionName)

        collectionCallback(collection)
    }

    inline fun <reified T: MongoDbDocument> performSave(entity: E, documentCompanion: MongoDbDomainEntityDocument<E, T>) {
        writeQuery<T>(collection) { collection ->
            val document = collection.findOne(documentIdProp eq entity.id())

            if (document != null) {
                collection.updateOne(documentIdProp eq entity.id(), documentCompanion.fromEntity(entity, document))
            } else {
                collection.insertOne(documentCompanion.fromEntity(entity))
            }
        }
    }

    open fun performDelete(entity: E) {
        writeQuery<MongoDbDocument>(collection) { collection ->
            collection.deleteOne(documentIdProp eq entity.id())
        }
    }
}
