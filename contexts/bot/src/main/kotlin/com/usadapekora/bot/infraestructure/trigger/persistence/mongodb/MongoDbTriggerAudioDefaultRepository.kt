package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefaultRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseId
import com.usadapekora.bot.domain.trigger.audio.TriggerDefaultAudioResponse
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbRepository
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class MongoDbTriggerAudioDefaultRepository : MongoDbRepository<TriggerDefaultAudioResponse>(
    collection = "triggerAudioDefault",
    documentIdProp = TriggerAudioDefaultDocument::id
), TriggerAudioDefaultRepository {

    override fun find(id: TriggerAudioResponseId): Either<TriggerAudioResponseException, TriggerDefaultAudioResponse> {
        val audio = oneQuery<TriggerAudioDefaultDocument>(collection) { collection ->
            collection.findOne(TriggerAudioDefaultDocument::id eq id.value)
        }

        if (audio != null) {
            return audio.toEntity().right()
        }

        return TriggerAudioResponseException.NotFound("Trigger audio with id $id not found").left()
    }

    override fun findByTrigger(id: Trigger.TriggerId): Either<TriggerAudioResponseException, TriggerDefaultAudioResponse> {
        val audio = oneQuery<TriggerAudioDefaultDocument>(collection) { collection ->
            collection.findOne(TriggerAudioDefaultDocument::trigger eq id.value)
        }

        if (audio != null) {
            return audio.toEntity().right()
        }

        return TriggerAudioResponseException.NotFound("Trigger audio of trigger with id $id not found").left()
    }

    override fun save(entity: TriggerDefaultAudioResponse) {
        performSave<TriggerAudioDefaultDocument>(entity, TriggerAudioDefaultDocument.Companion)
    }

    override fun delete(entity: TriggerDefaultAudioResponse) {
        performDelete(entity)
    }
}
