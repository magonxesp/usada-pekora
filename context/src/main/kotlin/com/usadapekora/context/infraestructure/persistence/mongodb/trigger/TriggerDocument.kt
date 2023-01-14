package com.usadapekora.context.infraestructure.persistence.mongodb.trigger

import com.usadapekora.context.domain.trigger.Trigger
import com.usadapekora.context.infraestructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.context.infraestructure.persistence.mongodb.MongoDbDomainEntityDocument
import org.bson.types.ObjectId

class TriggerDocument(
    val _id: ObjectId? = null,
    val id: String = "",
    val input: String = "",
    val compare: String = "",
    val outputText: String? = null,
    val discordGuildId: String = ""
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<Trigger, TriggerDocument>(TriggerDocument()) {
        override fun fromEntity(entity: Trigger, document: TriggerDocument) = TriggerDocument(
            _id = document._id,
            id = entity.id.value,
            input = entity.input.value,
            compare = entity.compare.toString(),
            outputText = entity.outputText.value,
            discordGuildId = entity.discordGuildId.value
        )
    }

    fun toEntity() = Trigger.fromPrimitives(
        id = id,
        input = input,
        compare = compare,
        outputText = outputText,
        discordGuildId = discordGuildId
    )
}
