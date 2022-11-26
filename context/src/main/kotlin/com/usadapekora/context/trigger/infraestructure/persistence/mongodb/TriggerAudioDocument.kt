package com.usadapekora.context.trigger.infraestructure.persistence.mongodb

import com.usadapekora.context.trigger.domain.TriggerAudio
import org.bson.types.ObjectId

class TriggerAudioDocument(
    val _id: ObjectId? = null,
    val id: String = "",
    val trigger: String = "",
    val name: String = "",
    val path: String = ""
) {
    companion object {
        fun fromAggregate(aggregate: TriggerAudio, document: TriggerAudioDocument = TriggerAudioDocument()) = TriggerAudioDocument(
            _id = document._id,
            id = aggregate.id.value,
            trigger = aggregate.trigger.toString(),
            name = aggregate.name.value,
            path = aggregate.path.value
        )
    }

    fun toAggregate() = TriggerAudio.fromPrimitives(
        id = id,
        trigger = trigger,
        name = name,
        path = path
    )
}
