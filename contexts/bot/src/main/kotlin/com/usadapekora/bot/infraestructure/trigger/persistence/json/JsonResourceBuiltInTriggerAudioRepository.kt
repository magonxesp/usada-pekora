package com.usadapekora.bot.infraestructure.trigger.persistence.json

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.BuiltInTriggerRepository
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.audio.BuiltInTriggerAudioResponseRepository
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class JsonResourceBuiltInTriggerAudioRepository : BuiltInTriggerAudioResponseRepository {
    @Serializable
    private class Collection(val data: Array<TriggerJson>)

    private val collection: Collection
        get() = this::class.java
            .getResource("/builtin_triggers.json")!!
            .readText()
            .let { Json.decodeFromString(it) }

    override fun find(id: TriggerAudioResponse.TriggerAudioResponseId): Either<TriggerAudioResponseException.NotFound, TriggerAudioResponse> =
        collection.data.firstOrNull { it.responseAudio?.id == id.value }?.responseAudio?.toEntity()?.right()
            ?: TriggerAudioResponseException.NotFound("Trigger response audio with id ${id.value} not found").left()
}
