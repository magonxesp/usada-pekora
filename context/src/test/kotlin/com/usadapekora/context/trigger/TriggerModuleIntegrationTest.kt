package com.usadapekora.context.trigger

import com.usadapekora.context.trigger.domain.Trigger
import com.usadapekora.context.trigger.domain.TriggerMother
import com.usadapekora.context.trigger.infraestructure.persistence.mongodb.MongoDbTriggerRepository

open class TriggerModuleIntegrationTest {

    private val repository = MongoDbTriggerRepository()

    /**
     * Creates a test user and delete from the database after test
     */
    protected fun databaseTestGuildPreferences(
        trigger: Trigger = TriggerMother.create(),
        save: Boolean = true,
        delete: Boolean = true,
        test: (trigger: Trigger) -> Unit
    ) {
        if (save) {
            repository.save(trigger)
        }

        test(trigger)

        if (delete) {
            repository.delete(trigger)
        }
    }

}
