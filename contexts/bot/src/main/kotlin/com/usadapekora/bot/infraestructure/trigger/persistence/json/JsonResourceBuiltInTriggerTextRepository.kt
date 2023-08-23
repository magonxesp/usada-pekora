package com.usadapekora.bot.infraestructure.trigger.persistence.json

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.BuiltInTriggerRepository
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerException
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponse
import com.usadapekora.bot.domain.trigger.audio.TriggerAudioResponseException
import com.usadapekora.bot.domain.trigger.text.BuiltInTriggerTextResponseRepository
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponse
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseException
import com.usadapekora.bot.domain.trigger.text.TriggerTextResponseId
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class JsonResourceBuiltInTriggerTextRepository : BuiltInTriggerTextResponseRepository {
    @Serializable
    private class Collection(val data: Array<TriggerJson>)

    private val collection: Collection
        get() = this::class.java
            .getResource("/builtin_triggers.json")!!
            .readText()
            .let { Json.decodeFromString(it) }

    override fun find(id: TriggerTextResponseId): Either<TriggerTextResponseException.NotFound, TriggerTextResponse> =
        collection.data.firstOrNull { it.responseText?.id == id.value }?.responseText?.toEntity()?.right()
            ?: TriggerTextResponseException.NotFound("Trigger response text with id ${id.value} not found").left()
}
