package com.usadapekora.bot.infraestructure.persistence.mongodb.trigger

import com.usadapekora.bot.domain.trigger.audio.TriggerAudioDefault
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbDomainEntityDocument
import org.bson.types.ObjectId

class TriggerAudioDefaultDocument(
    val _id: ObjectId? = null,
    val id: String = "",
    val trigger: String = "",
    val guild: String = "",
    val file: String = "",
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<TriggerAudioDefault, TriggerAudioDefaultDocument>(TriggerAudioDefaultDocument()) {
        override fun fromEntity(entity: TriggerAudioDefault, document: TriggerAudioDefaultDocument) = TriggerAudioDefaultDocument(
            _id = document._id,
            id = entity.id.value,
            trigger = entity.trigger.value,
            guild = entity.guild.value,
            file = entity.file.value
        )
    }

    fun toEntity() = TriggerAudioDefault.fromPrimitives(
        id = id,
        trigger = trigger,
        guild = guild,
        file = file
    )
}
