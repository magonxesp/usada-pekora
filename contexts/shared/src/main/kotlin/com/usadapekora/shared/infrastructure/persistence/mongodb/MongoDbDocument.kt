package com.usadapekora.shared.infrastructure.persistence.mongodb

import org.bson.types.ObjectId

abstract class MongoDbDocument(
    val _id: ObjectId? = null
)
