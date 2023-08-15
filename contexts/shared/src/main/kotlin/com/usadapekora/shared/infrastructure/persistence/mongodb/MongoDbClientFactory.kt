package com.usadapekora.shared.infrastructure.persistence.mongodb

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import com.usadapekora.shared.mongoConnectionUrl
import com.usadapekora.shared.mongoDatabase
import org.litote.kmongo.KMongo
import kotlin.concurrent.thread

object MongoDbClientFactory {
    private var client: MongoClient? = null

    private fun connect(): MongoClient {
        if (client == null) {
            client = KMongo.createClient(mongoConnectionUrl)

            Runtime.getRuntime().addShutdownHook(thread(start = false) {
                client!!.close()
                client = null
            })
        }

        return client!!
    }

    fun getDatabase(): MongoDatabase = connect().getDatabase(mongoDatabase)
    fun getClient() = connect()
}
