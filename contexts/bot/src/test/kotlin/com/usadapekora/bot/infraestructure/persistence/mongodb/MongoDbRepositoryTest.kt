package com.usadapekora.bot.infraestructure.persistence.mongodb

import com.usadapekora.bot.domain.ObjectMother
import com.usadapekora.shared.domain.Entity

open class MongoDbRepositoryTest<E : Entity, Repository : MongoDbRepository<E, *>>(
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
