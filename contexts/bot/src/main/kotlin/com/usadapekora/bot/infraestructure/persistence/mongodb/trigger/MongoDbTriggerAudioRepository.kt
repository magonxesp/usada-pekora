package com.usadapekora.bot.infraestructure.persistence.mongodb.trigger

import com.usadapekora.bot.domain.trigger.*

class MongoDbTriggerAudioRepository : TriggerAudioResponseRepository {

    private fun providerRepository(provider: TriggerAudioProvider): TriggerAudioResponseProviderRepository<*> = when(provider) {
        TriggerAudioProvider.DEFAULT -> MongoDbTriggerAudioDefaultRepository()
    }

    override fun find(id: TriggerAudioResponseId, provider: TriggerAudioProvider): TriggerAudioResponse {
        return providerRepository(provider).find(id)
    }

    override fun findByTrigger(id: Trigger.TriggerId, provider: TriggerAudioProvider): TriggerAudioResponse {
        return providerRepository(provider).findByTrigger(id)
    }

}
