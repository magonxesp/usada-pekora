package com.usadapekora.bot.infraestructure.persistence.mongodb.trigger

import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbRepository
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerAudio
import com.usadapekora.bot.domain.trigger.TriggerAudioException
import com.usadapekora.bot.domain.trigger.TriggerAudioRepository
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class MongoDbTriggerAudioRepository : MongoDbRepository<TriggerAudio, TriggerAudioDocument>(
    collection = "triggerAudio",
    documentIdProp = TriggerAudioDocument::id,
    documentCompanion = TriggerAudioDocument.Companion
), TriggerAudioRepository {

    override fun find(id: TriggerAudio.TriggerAudioId): TriggerAudio {
        val audio = oneQuery<TriggerAudioDocument>(collection) { collection ->
            collection.findOne(TriggerAudioDocument::id eq id.value)
        }

        if (audio != null) {
            return audio.toAggregate()
        }

        throw TriggerAudioException.NotFound("Trigger audio with id $id not found")
    }

    override fun findByTrigger(id: Trigger.TriggerId): TriggerAudio {
        val audio = oneQuery<TriggerAudioDocument>(collection) { collection ->
            collection.findOne(TriggerAudioDocument::trigger eq id.value)
        }

        if (audio != null) {
            return audio.toAggregate()
        }

        throw TriggerAudioException.NotFound("Trigger audio of trigger with id $id not found")
    }

}
