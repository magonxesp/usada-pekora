package com.usadapekora.context.trigger.infraestructure.persistence.mongodb

import com.usadapekora.context.trigger.domain.Trigger
import org.bson.types.ObjectId

class TriggerDocument(
    val _id: ObjectId? = null,
    val id: String = "",
    val input: String = "",
    val compare: String = "",
    val outputText: String? = null,
    val discordGuildId: String = ""
) {
    companion object {
        fun fromAggregate(aggregate: Trigger, document: TriggerDocument = TriggerDocument()) = TriggerDocument(
            _id = document._id,
            id = aggregate.id.value,
            input = aggregate.input.value,
            compare = aggregate.compare.toString(),
            outputText = aggregate.outputText.value,
            discordGuildId = aggregate.discordGuildId.value
        )
    }

    fun toAggregate() = Trigger.fromPrimitives(
        id = id,
        input = input,
        compare = compare,
        outputText = outputText,
        discordGuildId = discordGuildId
    )
}
