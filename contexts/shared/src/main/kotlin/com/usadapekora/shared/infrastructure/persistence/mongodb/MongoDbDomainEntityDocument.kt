package com.usadapekora.shared.infrastructure.persistence.mongodb

import com.usadapekora.shared.domain.common.Entity

abstract class MongoDbDomainEntityDocument<E : Entity, D>(protected val documentInstance: D) {
    abstract fun fromEntity(entity: E, document: D = documentInstance): D
}
