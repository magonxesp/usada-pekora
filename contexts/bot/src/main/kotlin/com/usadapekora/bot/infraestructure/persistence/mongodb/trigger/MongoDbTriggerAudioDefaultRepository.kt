package com.usadapekora.bot.infraestructure.persistence.mongodb.trigger

import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbRepository
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.audio.TriggerDefaultAudioResponse
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class MongoDbTriggerAudioDefaultRepository : MongoDbRepository<TriggerDefaultAudioResponse, TriggerAudioDefaultDocument>(
    collection = "triggerAudioDefault",
    documentIdProp = TriggerAudioDefaultDocument::id,
    documentCompanion = TriggerAudioDefaultDocument
), TriggerAudioDefaultRepository {

    override fun find(id: TriggerAudioResponseId): TriggerDefaultAudioResponse {
        val audio = oneQuery<TriggerAudioDefaultDocument>(collection) { collection ->
            collection.findOne(TriggerAudioDefaultDocument::id eq id.value)
        }

        if (audio != null) {
            return audio.toEntity()
        }

        throw TriggerAudioResponseException.NotFound("Trigger audio with id $id not found")
    }

    override fun findByTrigger(id: Trigger.TriggerId): TriggerDefaultAudioResponse {
        val audio = oneQuery<TriggerAudioDefaultDocument>(collection) { collection ->
            collection.findOne(TriggerAudioDefaultDocument::trigger eq id.value)
        }

        if (audio != null) {
            return audio.toEntity()
        }

        throw TriggerAudioResponseException.NotFound("Trigger audio of trigger with id $id not found")
    }

}
