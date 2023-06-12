package com.usadapekora.bot.infraestructure

import com.usadapekora.bot.domain.ObjectMother
import com.usadapekora.shared.domain.Entity
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbRepository

open class MongoDbRepositoryTestCase<E : Entity, Repository : MongoDbRepository<E, *>>(
    protected val repository: Repository,
    protected val mother: ObjectMother<E>
) {

    /**
     * Creates a test trigger and delete from the database after test
     */
    protected fun databaseTest(
        aggregate: E = mother.random(),
        save: Boolean = true,
        delete: Boolean = true,
        test: (aggregate: E) -> Unit
    ) {
        if (save) {
            repository.save(aggregate)
        }

        test(aggregate)

        if (delete) {
            repository.delete(aggregate)
        }
    }
}
