package com.usadapekora.context.trigger.infraestructure.persistence.mongodb

import com.usadapekora.context.shared.infraestructure.persistence.MongoDbRepository
import com.usadapekora.context.trigger.domain.Trigger
import com.usadapekora.context.trigger.domain.TriggerAudio
import com.usadapekora.context.trigger.domain.TriggerAudioException
import com.usadapekora.context.trigger.domain.TriggerAudioRepository
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.updateOne

class MongoDbTriggerAudioRepository : MongoDbRepository(), TriggerAudioRepository {

    private val collectionName = "triggerAudio"

    override fun find(id: TriggerAudio.TriggerAudioId): TriggerAudio {
        val audio = oneQuery<TriggerAudioDocument>(collectionName) { collection ->
            collection.findOne(TriggerAudioDocument::id eq id.value)
        }

        if (audio != null) {
            return audio.toAggregate()
        }

        throw TriggerAudioException.NotFound("Trigger audio with id $id not found")
    }

    override fun findByTrigger(id: Trigger.TriggerId): TriggerAudio {
        val audio = oneQuery<TriggerAudioDocument>(collectionName) { collection ->
            collection.findOne(TriggerAudioDocument::trigger eq id.value)
        }

        if (audio != null) {
            return audio.toAggregate()
        }

        throw TriggerAudioException.NotFound("Trigger audio of trigger with id $id not found")
    }

    override fun save(audio: TriggerAudio) {
        writeQuery<TriggerAudioDocument>(collectionName) { collection ->
            val document = collection.findOne(TriggerAudioDocument::id eq audio.id.value)

            if (document != null) {
                collection.updateOne(TriggerAudioDocument.fromAggregate(audio, document))
            } else {
                collection.insertOne(TriggerAudioDocument.fromAggregate(audio))
            }
        }
    }

    override fun delete(audio: TriggerAudio) {
        writeQuery<TriggerAudioDocument>(collectionName) { collection ->
            collection.deleteOne(TriggerAudioDocument::id eq audio.id.value)
        }
    }
}
