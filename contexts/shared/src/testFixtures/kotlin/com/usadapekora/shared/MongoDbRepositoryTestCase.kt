package com.usadapekora.shared

import com.usadapekora.shared.domain.Entity
import com.usadapekora.shared.domain.ObjectMother
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDomainEntityDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbRepository

open class MongoDbRepositoryTestCase<E : Entity, Repository : MongoDbRepository<E>>(
    protected val repository: Repository,
    protected val mother: ObjectMother<E>,
) {

    /**
     * Creates a test and delete from the database after test
     */
    protected inline fun <reified T : MongoDbDocument> runMongoDbRepositoryTest(
        documentCompanion: MongoDbDomainEntityDocument<E, T>,
        aggregate: E = mother.random(),
        save: Boolean = true,
        delete: Boolean = true,
        test: (aggregate: E) -> Unit
    ) {
        if (save) {
            repository.performSave(aggregate, documentCompanion)
        }

        test(aggregate)

        if (delete) {
            repository.performDelete(aggregate)
        }
    }
}
