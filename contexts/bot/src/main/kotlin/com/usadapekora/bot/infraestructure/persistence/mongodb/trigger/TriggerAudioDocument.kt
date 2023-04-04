package com.usadapekora.bot.infraestructure.persistence.mongodb.trigger

import com.usadapekora.bot.domain.trigger.TriggerAudio
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbDomainEntityDocument
import org.bson.types.ObjectId

class TriggerAudioDocument(
    val _id: ObjectId? = null,
    val id: String = "",
    val trigger: String = "",
    val guild: String = "",
    val file: String = "",
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<TriggerAudio, TriggerAudioDocument>(TriggerAudioDocument()) {
        override fun fromEntity(entity: TriggerAudio, document: TriggerAudioDocument) = TriggerAudioDocument(
            _id = document._id,
            id = entity.id.value,
            trigger = entity.trigger.value,
            guild = entity.guild.value,
            file = entity.file.value
        )
    }

    fun toAggregate() = TriggerAudio.fromPrimitives(
        id = id,
        trigger = trigger,
        guild = guild,
        file = file
    )
}
