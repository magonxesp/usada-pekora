package com.usadapekora.shared.infrastructure.persistence.mongodb

import com.mongodb.client.ClientSession
import com.usadapekora.shared.domain.PersistenceTransaction

class MongoDbPersistenceTransaction : PersistenceTransaction {
    private var session: ClientSession? = null

    override fun start() {
        session = MongoDbClientFactory.getClient().startSession()
        session?.startTransaction()
    }

    override fun rollback() {
        session?.abortTransaction()
        session = null
    }

    override fun commit() {
        session?.commitTransaction()
        session = null
    }
}
