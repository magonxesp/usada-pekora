package com.usadapekora.bot.infraestructure.persistence.mongodb.trigger

import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbRepository
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefault
import com.usadapekora.bot.domain.trigger.exception.TriggerAudioResponseException
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class MongoDbTriggerAudioDefaultRepository : MongoDbRepository<TriggerAudioDefault, TriggerAudioDefaultDocument>(
    collection = "triggerAudioDefault",
    documentIdProp = TriggerAudioDefaultDocument::id,
    documentCompanion = TriggerAudioDefaultDocument
), TriggerAudioDefaultRepository {

    override fun find(id: TriggerAudioResponseId): TriggerAudioDefault {
        val audio = oneQuery<TriggerAudioDefaultDocument>(collection) { collection ->
            collection.findOne(TriggerAudioDefaultDocument::id eq id.value)
        }

        if (audio != null) {
            return audio.toEntity()
        }

        throw TriggerAudioResponseException.NotFound("Trigger audio with id $id not found")
    }

    override fun findByTrigger(id: Trigger.TriggerId): TriggerAudioDefault {
        val audio = oneQuery<TriggerAudioDefaultDocument>(collection) { collection ->
            collection.findOne(TriggerAudioDefaultDocument::trigger eq id.value)
        }

        if (audio != null) {
            return audio.toEntity()
        }

        throw TriggerAudioResponseException.NotFound("Trigger audio of trigger with id $id not found")
    }

}
