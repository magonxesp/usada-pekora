package com.usadapekora.bot.infraestructure.persistence.mongodb.trigger

import arrow.core.Either
import com.usadapekora.bot.domain.trigger.*
import com.usadapekora.bot.domain.trigger.audio.*

class MongoDbTriggerAudioRepository : TriggerAudioResponseRepository {

    private fun providerRepository(provider: TriggerAudioResponseProvider): TriggerAudioResponseProviderRepository<*> = when(provider) {
        TriggerAudioResponseProvider.DEFAULT -> MongoDbTriggerAudioDefaultRepository()
    }

    override fun find(id: TriggerAudioResponseId, provider: TriggerAudioResponseProvider)
        = providerRepository(provider).find(id)

    override fun findByTrigger(id: Trigger.TriggerId, provider: TriggerAudioResponseProvider)
        = providerRepository(provider).findByTrigger(id)

}
