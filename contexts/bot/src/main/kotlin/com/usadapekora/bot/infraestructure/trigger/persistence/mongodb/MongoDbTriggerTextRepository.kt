package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponse
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseId
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseRepository
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbRepository
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class MongoDbTriggerTextRepository : MongoDbRepository<TriggerTextResponse, TriggerTextResponseDocument>(
    collection = "triggerText",
    documentIdProp = TriggerTextResponseDocument::id,
    documentCompanion = TriggerTextResponseDocument
), TriggerTextResponseRepository {
    override fun find(id: TriggerTextResponseId): Either<TriggerTextResponseException, TriggerTextResponse> {
        val text = oneQuery<TriggerTextResponseDocument>(collection) { collection ->
            collection.findOne(TriggerTextResponseDocument::id eq id.value)
        }

        if (text != null) {
            return text.toEntity().right()
        }

        return TriggerTextResponseException.NotFound("Trigger text response with id $id not found").left()
    }

    override fun save(entity: TriggerTextResponse) {
        performSave(entity)
    }

    override fun delete(entity: TriggerTextResponse) {
        performDelete(entity)
    }
}
