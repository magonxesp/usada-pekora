package com.usadapekora.bot.infraestructure.persistence.mongodb.trigger

import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.bot.infraestructure.persistence.mongodb.MongoDbDomainEntityDocument
import org.bson.types.ObjectId

class TriggerDocument(
    val _id: ObjectId? = null,
    val id: String = "",
    val title: String = "",
    val input: String = "",
    val compare: String = "",
    val responseTextId: String? = null,
    val responseAudioId: String? = null,
    val responseAudioProvider: String? = null,
    val discordGuildId: String = ""
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<Trigger, TriggerDocument>(TriggerDocument()) {
        override fun fromEntity(entity: Trigger, document: TriggerDocument) = TriggerDocument(
            _id = document._id,
            id = entity.id.value,
            title = entity.title.value,
            input = entity.input.value,
            compare = entity.compare.toString(),
            responseTextId = entity.responseText?.value,
            responseAudioId = entity.responseAudio?.value,
            responseAudioProvider = entity.responseAudioProvider?.value,
            discordGuildId = entity.discordGuildId.value
        )
    }

    fun toEntity() = Trigger.fromPrimitives(
        id = id,
        title = title.ifBlank { "untitled" },
        input = input.ifBlank { "[empty]" },
        compare = compare,
        responseTextId = responseTextId,
        responseAudioId = responseAudioId,
        responseAudioProvider = responseAudioProvider,
        discordGuildId = discordGuildId
    )
}
