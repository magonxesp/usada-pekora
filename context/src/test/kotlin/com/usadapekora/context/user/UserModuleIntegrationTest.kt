package com.usadapekora.context.user

import com.usadapekora.context.user.domain.User
import com.usadapekora.context.user.domain.UserMother
import com.usadapekora.context.user.infraestructure.persistence.MongoDbUserRepository

open class UserModuleIntegrationTest {

    private val repository = MongoDbUserRepository()

    /**
     * Creates a test user and delete from the database after test
     */
    protected fun databaseTestUser(
        user: User = UserMother.create(),
        save: Boolean = true,
        delete: Boolean = true,
        test: (user: User) -> Unit
    ) {
        if (save) {
            repository.save(user)
        }

        test(user)

        if (delete) {
            repository.delete(user)
        }
    }

}
