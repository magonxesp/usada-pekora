package com.usadapekora.context.infraestructure.persistence.mongodb.trigger

import com.usadapekora.context.domain.trigger.TriggerAudio
import com.usadapekora.context.infraestructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.context.infraestructure.persistence.mongodb.MongoDbDomainEntityDocument
import org.bson.types.ObjectId

class TriggerAudioDocument(
    val _id: ObjectId? = null,
    val id: String = "",
    val trigger: String = "",
    val name: String = "",
    val path: String = ""
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<TriggerAudio, TriggerAudioDocument>(TriggerAudioDocument()) {
        override fun fromEntity(entity: TriggerAudio, document: TriggerAudioDocument) = TriggerAudioDocument(
            _id = document._id,
            id = entity.id.value,
            trigger = entity.trigger.value,
            name = entity.name.value,
            path = entity.path.value
        )
    }

    fun toAggregate() = TriggerAudio.fromPrimitives(
        id = id,
        trigger = trigger,
        name = name,
        path = path
    )
}
