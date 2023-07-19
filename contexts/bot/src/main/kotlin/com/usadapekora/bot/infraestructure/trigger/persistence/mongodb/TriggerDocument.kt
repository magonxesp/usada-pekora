package com.usadapekora.bot.infraestructure.trigger.persistence.mongodb

import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDocument
import com.usadapekora.shared.infrastructure.persistence.mongodb.MongoDbDomainEntityDocument

class TriggerDocument(
    val id: String = "",
    val title: String = "",
    val input: String = "",
    val compare: String = "",
    val kind: String = "",
    val responseTextId: String? = null,
    val responseAudioId: String? = null,
    val responseAudioProvider: String? = null,
    val guildId: String = ""
): MongoDbDocument() {
    companion object : MongoDbDomainEntityDocument<Trigger, TriggerDocument>({ TriggerDocument() }) {
        override fun fromEntity(entity: Trigger, document: TriggerDocument) = TriggerDocument(
            id = entity.id.value,
            title = entity.title.value,
            input = entity.input.value,
            compare = entity.compare.toString(),
            kind = entity.kind.value,
            responseTextId = entity.responseText?.value,
            responseAudioId = entity.responseAudio?.value,
            responseAudioProvider = entity.responseAudioProvider?.value,
            guildId = entity.guildId.value
        )
    }

    fun toEntity() = Trigger.fromPrimitives(
        id = id,
        title = title.ifBlank { "untitled" },
        input = input.ifBlank { "[empty]" },
        compare = compare,
        kind = kind.ifBlank { "private" },
        responseTextId = responseTextId,
        responseAudioId = responseAudioId,
        responseAudioProvider = responseAudioProvider,
        guildId = guildId
    )
}
