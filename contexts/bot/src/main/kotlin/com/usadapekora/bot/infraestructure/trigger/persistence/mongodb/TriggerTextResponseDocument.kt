package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import com.usadapekora.bot.domain.trigger.text.TriggerTextResponse
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDomainEntityDocument

class TriggerTextResponseDocument(
    val id: String = "",
    val content: String = "",
    val type: String = ""
) : MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<TriggerTextResponse, TriggerTextResponseDocument>({ TriggerTextResponseDocument() }) {
        override fun fromEntity(entity: TriggerTextResponse, document: TriggerTextResponseDocument) = TriggerTextResponseDocument(
            id = entity.id.value,
            content = entity.content.value,
            type = entity.type.value
        )
    }

    fun toEntity() = TriggerTextResponse.fromPrimitives(
        id = id,
        content = content,
        type = type
    )
}
