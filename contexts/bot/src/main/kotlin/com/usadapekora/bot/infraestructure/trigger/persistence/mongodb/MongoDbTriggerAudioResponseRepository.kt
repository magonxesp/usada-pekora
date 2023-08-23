package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.audio.*
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbRepository
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.aggregate
import org.litote.kmongo.MongoOperator.lookup
import org.litote.kmongo.MongoOperator.match

class MongoDbTriggerAudioResponseRepository : MongoDbRepository<TriggerAudioResponse>(
    collection = "triggerAudio",
    documentIdProp = TriggerAudioResponseDocument::id
), TriggerAudioResponseRepository {

    override fun find(id: TriggerAudioResponse.TriggerAudioResponseId): Either<TriggerAudioResponseException.NotFound, TriggerAudioResponse> {
        val audio = oneQuery<TriggerAudioResponseDocument>(collection) { collection ->
            collection.findOne(TriggerAudioResponseDocument::id eq id.value)
        }

        if (audio != null) {
            return audio.toEntity().right()
        }

        return TriggerAudioResponseException.NotFound("Trigger audio with id $id not found").left()
    }

    override fun findByTrigger(id: Trigger.TriggerId): Either<TriggerAudioResponseException.NotFound, TriggerAudioResponse> {
        val audio = oneQuery<TriggerAudioResponseDocument>(collection) { collection ->
            collection.aggregate<TriggerAudioResponseDocument>("""
                [
                    {
                        $lookup:
                        {
                            from: "triggers",
                            localField: "id",
                            foreignField: "responseAudioId",
                            as: "triggers",
                        },
                    },
                    {
                        $match:
                        {
                            "triggers.id": "${id.value}",
                        },
                    },
                ]
            """).first()
        }

        if (audio != null) {
            return audio.toEntity().right()
        }

        return TriggerAudioResponseException.NotFound("Trigger audio of trigger with id $id not found").left()
    }

    override fun save(entity: TriggerAudioResponse) {
        performSave<TriggerAudioResponseDocument>(entity, TriggerAudioResponseDocument.Companion)
    }

    override fun delete(entity: TriggerAudioResponse) {
        performDelete(entity)
    }
}
