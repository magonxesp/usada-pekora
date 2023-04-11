package com.usadapekora.bot.infraestructure.persistence.mongodb.trigger

import com.usadapekora.bot.domain.trigger.*
import com.usadapekora.bot.domain.trigger.audio.*

class MongoDbTriggerAudioRepository : TriggerAudioResponseRepository {

    private fun providerRepository(provider: TriggerAudioResponseProvider): TriggerAudioResponseProviderRepository<*> = when(provider) {
        TriggerAudioResponseProvider.DEFAULT -> MongoDbTriggerAudioDefaultRepository()
    }

    override fun find(id: TriggerAudioResponseId, provider: TriggerAudioResponseProvider): TriggerAudioResponse {
        return providerRepository(provider).find(id)
    }

    override fun findByTrigger(id: Trigger.TriggerId, provider: TriggerAudioResponseProvider): TriggerAudioResponse {
        return providerRepository(provider).findByTrigger(id)
    }

}
