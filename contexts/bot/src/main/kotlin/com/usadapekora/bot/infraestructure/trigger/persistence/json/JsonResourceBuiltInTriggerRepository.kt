package com.usadapekora.bot.infraestructure.trigger.persistence.json

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.usadapekora.bot.domain.trigger.BuiltInTriggerRepository
import com.usadapekora.bot.domain.trigger.Trigger
import com.usadapekora.bot.domain.trigger.TriggerException
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class JsonResourceBuiltInTriggerRepository : BuiltInTriggerRepository {
    @Serializable
    private class Collection(val data: Array<TriggerJson>)

    private val collection: Collection
        get() = this::class.java
            .getResource("/builtin_triggers.json")!!
            .readText()
            .let { Json.decodeFromString(it) }

    override fun findAll(): Array<Trigger> =
        collection.data.map { it.toEntity() }.toTypedArray()

    override fun find(id: Trigger.TriggerId): Either<TriggerException.NotFound, Trigger> =
        collection.data.firstOrNull { it.id == id.value }?.toEntity()?.right()
            ?: TriggerException.NotFound("Trigger with id ${id.value} not found").left()
}
