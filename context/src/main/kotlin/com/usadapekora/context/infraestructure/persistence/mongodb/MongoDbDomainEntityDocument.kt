package com.usadapekora.context.infraestructure.persistence.mongodb

import com.usadapekora.context.domain.shared.Entity

abstract class MongoDbDomainEntityDocument<E : Entity, D>(protected val documentInstance: D) {
    abstract fun fromEntity(entity: E, document: D = documentInstance): D
}
