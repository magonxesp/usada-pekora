package com.usadapekora.context.trigger.infraestructure.mongodb

import com.usadapekora.context.trigger.domain.Trigger
import org.bson.types.ObjectId

class TriggerDocument(
    val _id: ObjectId? = null,
    val id: String = "",
    val input: String = "",
    val compare: String = "",
    val outputText: String? = null,
    val outputSound: String? = null,
    val discordGuildId: String = ""
) {
    companion object {
        fun fromAggregate(aggregate: Trigger, document: TriggerDocument = TriggerDocument()) = TriggerDocument(
            _id = document._id,
            id = aggregate.id,
            input = aggregate.input,
            compare = aggregate.compare.toString(),
            outputText = aggregate.outputText,
            outputSound = aggregate.outputSound,
            discordGuildId = aggregate.discordGuildId
        )
    }

    fun toAggregate() = Trigger.fromPrimitives(
        id = id,
        input = input,
        compare = compare,
        outputText = outputText,
        outputSound = outputSound,
        discordGuildId = discordGuildId
    )
}
