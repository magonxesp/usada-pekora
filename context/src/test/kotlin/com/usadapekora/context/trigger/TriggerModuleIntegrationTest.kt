package com.usadapekora.context.trigger

import com.usadapekora.context.trigger.domain.Trigger
import com.usadapekora.context.trigger.domain.TriggerAudio
import com.usadapekora.context.trigger.domain.TriggerAudioMother
import com.usadapekora.context.trigger.domain.TriggerMother
import com.usadapekora.context.trigger.infraestructure.persistence.mongodb.MongoDbTriggerAudioRepository
import com.usadapekora.context.trigger.infraestructure.persistence.mongodb.MongoDbTriggerRepository

open class TriggerModuleIntegrationTest {

    private val triggerRepository = MongoDbTriggerRepository()
    private val triggerAudioRepository = MongoDbTriggerAudioRepository()

    /**
     * Creates a test trigger and delete from the database after test
     */
    protected fun databaseTestTrigger(
        trigger: Trigger = TriggerMother.create(),
        save: Boolean = true,
        delete: Boolean = true,
        test: (trigger: Trigger) -> Unit
    ) {
        if (save) {
            triggerRepository.save(trigger)
        }

        test(trigger)

        if (delete) {
            triggerRepository.delete(trigger)
        }
    }

    protected fun databaseTestTriggerAudio(
        triggerAudio: TriggerAudio = TriggerAudioMother.create(),
        save: Boolean = true,
        delete: Boolean = true,
        test: (triggerAudio: TriggerAudio) -> Unit
    ) {
        if (save) {
            triggerAudioRepository.save(triggerAudio)
        }

        test(triggerAudio)

        if (delete) {
            triggerAudioRepository.delete(triggerAudio)
        }
    }
}
