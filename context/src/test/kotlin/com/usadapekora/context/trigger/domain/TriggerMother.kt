package com.usadapekora.context.trigger.domain

import com.usadapekora.context.shared.domain.Random

object TriggerMother {
    fun create(
        id: String? = null,
        input: String? = null,
        compare: String? = null,
        outputText: String? = null,
        discordGuildId: String? = null
    ) = Trigger.fromPrimitives(
        id = id ?: Random.instance().random.nextUUID(),
        input = input ?: Random.instance().chiquito.expressions(),
        compare = compare ?: Trigger.TriggerCompare.values().random().toString(),
        outputText = outputText ?: Random.instance().chiquito.sentences(),
        discordGuildId = discordGuildId ?: java.util.Random().nextLong().toString()
    )
}
