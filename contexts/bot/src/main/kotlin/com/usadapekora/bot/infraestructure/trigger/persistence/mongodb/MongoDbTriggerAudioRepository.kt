package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseProvider
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseProviderRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseRepository

class MongoDbTriggerAudioRepository : TriggerAudioResponseRepository {

    private fun providerRepository(provider: TriggerAudioResponseProvider): TriggerAudioResponseProviderRepository<*> = when(provider) {
        TriggerAudioResponseProvider.DEFAULT -> MongoDbTriggerAudioDefaultRepository()
    }

    override fun find(id: TriggerAudioResponseId, provider: TriggerAudioResponseProvider)
        = providerRepository(provider).find(id)

    override fun findByTrigger(id: Trigger.TriggerId, provider: TriggerAudioResponseProvider)
        = providerRepository(provider).findByTrigger(id)

}
