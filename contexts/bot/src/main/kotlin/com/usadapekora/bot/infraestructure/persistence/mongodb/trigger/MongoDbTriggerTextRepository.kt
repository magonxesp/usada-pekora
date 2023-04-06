package com.usadapekora.bot.infraestructure.persistence.mongodb.trigger

import com.usadapekora.bot.domain.trigger.TriggerTextResponse
import com.usadapekora.bot.domain.trigger.TriggerTextResponseId
import com.usadapekora.bot.domain.trigger.TriggerTextResponseRepository
import com.usadapekora.bot.domain.trigger.exception.TriggerTextResponseException
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbRepository
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class MongoDbTriggerTextRepository : MongoDbRepository<TriggerTextResponse, TriggerTextResponseDocument>(
    collection = "triggerText",
    documentIdProp = TriggerTextResponseDocument::id,
    documentCompanion = TriggerTextResponseDocument.Companion
), TriggerTextResponseRepository {
    override fun find(id: TriggerTextResponseId): TriggerTextResponse {
        val text = oneQuery<TriggerTextResponseDocument>(collection) { collection ->
            collection.findOne(TriggerTextResponseDocument::id eq id.value)
        }

        if (text != null) {
            return text.toEntity()
        }

        throw TriggerTextResponseException.NotFound("Trigger text response with id $id not found")
    }
}
