package com.usadapekora.bot.infraestructure.persistence.mongodb

import com.usadapekora.shared.domain.Entity

abstract class MongoDbDomainEntityDocument<E : Entity, D>(protected val documentInstance: D) {
    abstract fun fromEntity(entity: E, document: D = documentInstance): D
}
